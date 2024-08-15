package com.ruoyi.web.controller.mind.domain;


import lombok.Data;

import java.util.Date;

/**
 * 笔记信息
对象 note_info
 *
 * @date 2022-08-17
 */

@Data
public class NodeInfo
{
    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 笔记Id
     */
    private String noteId;

    /**
     * 节点名称
     */
    private String nodeTopic;

    /**
     * 节点位置
     */
    private int nodeIndex;

    /**
     * 父节点Id
     */
    private String parentId;

    /**
     * 根结点Id
     */
    private String rootId;

    /**
     * 节点创建时间
     */
    private Date createTime;


}
