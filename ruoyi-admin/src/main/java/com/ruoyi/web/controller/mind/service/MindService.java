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
public interface MindService
{
    public AjaxResult findNodeInfo(String id);

    public void insertNodeInfo(String topic, String id, String parentId, String index, String topicRootId);

    public void editIndex(String id, String index, String parentId);

    public void removeNode(String id);
}
