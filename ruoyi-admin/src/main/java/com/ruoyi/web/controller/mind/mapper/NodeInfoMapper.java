package com.ruoyi.web.controller.mind.mapper;

import com.ruoyi.web.controller.mind.domain.NodeInfo;
import com.ruoyi.web.controller.mind.domain.NoteInfo;
import org.apache.ibatis.annotations.*;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;


import java.util.List;

/**
 * 笔记信息
Mapper接口
 * 
 * @date 2022-08-17
 */
@Mapper
public interface NodeInfoMapper
{

    @Insert("insert into node_info (id, note_id, node_topic, node_index, parent_id, root_id, create_time) values(#{id}, #{noteId}, #{nodeTopic}, #{nodeIndex}, #{parentId}, #{rootId}, #{createTime}) ")
    public int insertNodeInfo(NodeInfo nodeInfo);


    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "note_id", target = "noteId"),
            @Mapping(source = "node_topic", target = "nodeTopic"),
            @Mapping(source = "node_index", target = "nodeIndex"),
            @Mapping(source = "parent_id", target = "parentId"),
            @Mapping(source = "root_id", target = "rootId")
    })
    @Select("select id, note_id, node_topic, node_index, parent_id, root_id from node_info where note_id = #{id} order by node_index ASC")
    public List<NodeInfo> findNodeInfo(String id);

    @Select("select note_id from node_info where id = #{topicRootId} limit 1")
    public String getNoteInfoByRootId(String topicRootId);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "note_id", target = "noteId"),
            @Mapping(source = "node_topic", target = "nodeTopic"),
            @Mapping(source = "node_index", target = "nodeIndex"),
            @Mapping(source = "parent_id", target = "parentId"),
            @Mapping(source = "root_id", target = "rootId")
    })
    @Select("select id, note_id, node_topic, node_index, parent_id, root_id from node_info where id = #{id}")
    public NodeInfo getNodeInfoById(String id);

    @Update("update node_info set node_topic = #{topic} where id = #{id}")
    public void updateNodeInfoTopic(@Param("id") String id, @Param("topic") String topic);

    @Update("update node_info set node_index = #{index} where id = #{id}")
    public void updateNodeInfoIndex(@Param("id")String id, @Param("index") String index);

    @Update("update node_info set parent_id = #{parentId} where id = #{id}")
    public void updateNodeInfoParentId(@Param("id")String id, @Param("parentId") String parentId);

    @Delete("delete from node_info where id = #{id}")
    public void removeNodeById(String id);


}
