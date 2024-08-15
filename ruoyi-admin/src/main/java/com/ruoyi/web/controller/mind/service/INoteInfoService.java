package com.ruoyi.web.controller.mind.service;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.web.controller.mind.domain.NoteInfo;

import java.util.List;



/**
 * 笔记信息
Service接口
 * 
 * @author ruoyi
 * @date 2022-08-17
 */
public interface INoteInfoService 
{
    /**
     * 查询笔记信息

     * 
     * @param id 笔记信息
主键
     * @return 笔记信息

     */
    public NoteInfo selectNoteInfoById(Long id);

    /**
     * 查询笔记信息
列表
     * 
     * @param noteInfo 笔记信息

     * @return 笔记信息
集合
     */
    public List<NoteInfo> selectNoteInfoList(NoteInfo noteInfo);

    public List<NoteInfo> selectNoteInfoMainList(NoteInfo noteInfo);

    /**
     * 新增笔记信息

     * 
     * @param noteInfo 笔记信息

     * @return 结果
     */
    public int insertNoteInfo(NoteInfo noteInfo);

    /**
     * 修改笔记信息

     * 
     * @param noteInfo 笔记信息

     * @return 结果
     */
    public int updateNoteInfo(NoteInfo noteInfo);

    /**
     * 批量删除笔记信息

     * 
     * @param ids 需要删除的笔记信息
主键集合
     * @return 结果
     */
    public int deleteNoteInfoByIds(String ids);

    /**
     * 删除笔记信息
信息
     * 
     * @param id 笔记信息
主键
     * @return 结果
     */
    public int deleteNoteInfoById(Long id);

    public AjaxResult share(String id);

    public AjaxResult notShare(String id);
}
