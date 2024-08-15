package com.ruoyi.web.controller.mind.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.DateUtils;

import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.web.controller.mind.domain.Article;
import com.ruoyi.web.controller.mind.domain.NodeInfo;
import com.ruoyi.web.controller.mind.domain.NoteInfo;
import com.ruoyi.web.controller.mind.mapper.NodeInfoMapper;
import com.ruoyi.web.controller.mind.mapper.NoteInfoMapper;
import com.ruoyi.web.controller.mind.service.INoteInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 笔记信息
Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-08-17
 */
@Service
public class NoteInfoServiceImpl implements INoteInfoService
{
    @Autowired
    private NoteInfoMapper noteInfoMapper;

    @Autowired
    private NodeInfoMapper nodeInfoMapper;

    /**
     * 查询笔记信息

     * 
     * @param id 笔记信息
主键
     * @return 笔记信息

     */
    @Override
    public NoteInfo selectNoteInfoById(Long id)
    {
        return noteInfoMapper.selectNoteInfoById(id);
    }

    /**
     * 查询笔记信息
列表
     * 
     * @param noteInfo 笔记信息

     * @return 笔记信息

     */
    @Override
    public List<NoteInfo> selectNoteInfoList(NoteInfo noteInfo)
    {
        noteInfo.setCreateUser(ShiroUtils.getSysUser().getUserName());
        return noteInfoMapper.selectNoteInfoList(noteInfo);
    }

    @Override
    public List<NoteInfo> selectNoteInfoMainList(NoteInfo noteInfo)
    {
        noteInfo.setCreateUser(ShiroUtils.getSysUser().getUserName());
        return noteInfoMapper.selectNoteInfoMainList(noteInfo);
    }

    /**
     * 新增笔记信息

     * 
     * @param noteInfo 笔记信息

     * @return 结果
     */
    @Override
    @Transactional
    public int insertNoteInfo(NoteInfo noteInfo)
    {
        noteInfo.setCreateTime(DateUtils.getNowDate());
        noteInfo.setCreateUser(ShiroUtils.getSysUser().getUserName());
        noteInfo.setUpdateTime(DateUtils.getNowDate());
        int id = noteInfoMapper.insertNoteInfo(noteInfo);
        NodeInfo nodeInfo = new NodeInfo();
        nodeInfo.setNodeIndex(0);
        nodeInfo.setNodeTopic(noteInfo.getNoteName());
        nodeInfo.setParentId("");
        nodeInfo.setRootId(UUID.randomUUID().toString().substring(2,18).replace("-",""));
        nodeInfo.setId(UUID.randomUUID().toString().substring(2,18).replace("-", ""));
        nodeInfo.setNoteId(String.valueOf(noteInfo.getId()));
        nodeInfo.setCreateTime(new Date());

        return nodeInfoMapper.insertNodeInfo(nodeInfo);
    }

    /**
     * 修改笔记信息

     * 
     * @param noteInfo 笔记信息

     * @return 结果
     */
    @Override
    public int updateNoteInfo(NoteInfo noteInfo)
    {
        noteInfo.setUpdateTime(DateUtils.getNowDate());
        return noteInfoMapper.updateNoteInfo(noteInfo);
    }

    /**
     * 批量删除笔记信息

     * 
     * @param ids 需要删除的笔记信息
主键
     * @return 结果
     */
    @Override
    public int deleteNoteInfoByIds(String ids)
    {
        return noteInfoMapper.deleteNoteInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除笔记信息
信息
     * 
     * @param id 笔记信息
主键
     * @return 结果
     */
    @Override
    public int deleteNoteInfoById(Long id)
    {
        return noteInfoMapper.deleteNoteInfoById(id);
    }

    @Override
    public AjaxResult share(String id) {
        NoteInfo articleSource = noteInfoMapper.selectNoteInfoById(Long.valueOf(id));
        if (articleSource.getIsShare().equals("0")) {
            noteInfoMapper.editNoteShare(id);
            return AjaxResult.success("分享成功");

        } else {
            return AjaxResult.error("已分享，无法重复分享。");
        }

    }


    @Override
    public AjaxResult notShare(String id) {
        NoteInfo articleSource = noteInfoMapper.selectNoteInfoById(Long.valueOf(id));
        if (articleSource.getIsShare().equals("1")) {
            noteInfoMapper.editNoteNotShare(id);
            return AjaxResult.success("关闭分享完成");

        } else {
            return AjaxResult.error("已分享，无法重复分享。");
        }

    }
}
