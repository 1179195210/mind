package com.ruoyi.web.controller.mind.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.web.controller.mind.domain.NoteInfo;
import com.ruoyi.web.controller.mind.service.MindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 模态窗口
 *
 */
@Controller
@RequestMapping("/mind")
public class MindController
{

    private String prefix = "mind";

    @Autowired
    private MindService mindService;

    /**
     * 模态窗口
     */
    @PostMapping("/addChild")
    public String addChild(String topic, String id, String parentId, String index, String topicRootId)
    {
        mindService.insertNodeInfo(topic, id, parentId, index, topicRootId);
        return prefix + "/addChild";
    }

    @PostMapping("/editIndex")
    public String editIndex(String id, String index, String parentId)
    {
        mindService.editIndex(id, index, parentId);
        return prefix + "/addChild";
    }

    @PostMapping("/removeNode")
    public String removeNode(String id)
    {
        mindService.removeNode(id);
        return prefix + "/addChild";
    }

    @RequestMapping(value = "/findNodeInfo",method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult findNodeInfo(String id){
        return mindService.findNodeInfo(id);
    }


}
