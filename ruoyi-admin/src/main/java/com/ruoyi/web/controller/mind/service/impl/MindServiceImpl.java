package com.ruoyi.web.controller.mind.service.impl;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.web.controller.mind.domain.NodeInfo;
import com.ruoyi.web.controller.mind.domain.NoteInfo;
import com.ruoyi.web.controller.mind.mapper.NodeInfoMapper;
import com.ruoyi.web.controller.mind.mapper.NoteInfoMapper;
import com.ruoyi.web.controller.mind.service.MindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 笔记信息
Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-08-17
 */
@Service
public class MindServiceImpl implements MindService
{
    @Autowired
    private NodeInfoMapper nodeInfoMapper;

    @Autowired
    NoteInfoMapper noteInfoMapper;

    @Override
    public AjaxResult findNodeInfo(String id) {
        List<NodeInfo> nodeInfoList = nodeInfoMapper.findNodeInfo(id);
        NoteInfo nodeInfo = noteInfoMapper.selectNoteInfoById(Long.parseLong(nodeInfoList.get(0).getNoteId()));
        String currentUsername = ShiroUtils.getSysUser().getUserName();
        String msg;
        if (currentUsername.equals(nodeInfo.getCreateUser())) {
            msg = "查询成功";
        } else {
            msg = "非作者";
        }
        return new AjaxResult(AjaxResult.Type.SUCCESS, msg, nodeInfoList);
    }

    @Override
    public void insertNodeInfo(String topic, String id, String parentId, String index, String topicRootId) {
        //根据节点的Id判断是否已经存在，若不存在则插入 若存在则直接判断是否两个topic是否相同，不相同则修改
        NodeInfo nodeInfoSource = nodeInfoMapper.getNodeInfoById(id);
        if (nodeInfoSource != null) {
            if (!nodeInfoSource.getNodeTopic().equals(topic)) {
                nodeInfoMapper.updateNodeInfoTopic(id, topic);
            }
            if (nodeInfoSource.getNodeIndex() != Integer.valueOf(index)) {
                nodeInfoMapper.updateNodeInfoIndex(id, index);
            }
            if (!nodeInfoSource.getParentId().equals(parentId)) {
                nodeInfoMapper.updateNodeInfoParentId(id, parentId);
            }
        } else {
            // 根据根结点Id 查询出已存在的笔记Id
            String noteId = nodeInfoMapper.getNoteInfoByRootId(topicRootId);
            NodeInfo nodeInfo = new NodeInfo();
            nodeInfo.setNodeTopic(topic);
            nodeInfo.setId(id);
            nodeInfo.setParentId(parentId);
            nodeInfo.setNodeIndex(Integer.valueOf(index));
            nodeInfo.setRootId(topicRootId);
            nodeInfo.setNoteId(noteId);
            nodeInfoMapper.insertNodeInfo(nodeInfo);
        }

    }

    @Override
    public void editIndex(String id, String index, String parentId) {
        NodeInfo nodeInfoSource = nodeInfoMapper.getNodeInfoById(id);
        NoteInfo noteInfo = noteInfoMapper.selectNoteInfoById(Long.valueOf(nodeInfoSource.getNoteId()));
        if (ShiroUtils.getSysUser().getUserName().equals(noteInfo.getCreateUser())) {
            if (nodeInfoSource != null) {
                if (nodeInfoSource.getNodeIndex() != Integer.valueOf(index)) {
                    nodeInfoMapper.updateNodeInfoIndex(id, index);
                }
                if (!nodeInfoSource.getParentId().equals(parentId)) {
                    nodeInfoMapper.updateNodeInfoParentId(id, parentId);
                }
            }
        }

    }

    @Override
    public void removeNode(String id) {
        nodeInfoMapper.removeNodeById(id);
    }
}
