package com.ruoyi.web.controller.mind.mapper;

import com.ruoyi.web.controller.mind.domain.Article;
import com.ruoyi.web.controller.mind.domain.NodeInfo;
import org.apache.ibatis.annotations.*;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.Date;
import java.util.List;

/**
 * 笔记信息
Mapper接口
 * 
 */
@Mapper
public interface ArticleInfoMapper
{

    @Insert("insert into article (id, author, title, content, create_user, create_time, update_user, update_time) values(#{id}, #{author}, #{title}, #{content}, #{createUser}, #{createTime}, #{updateUser}, #{updateTime}) ")
    public void addArticle(Article article);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "author", target = "author"),
            @Mapping(source = "title", target = "title"),
            @Mapping(source = "content", target = "content"),
            @Mapping(source = "create_user", target = "createUser"),
            @Mapping(source = "create_time", target = "createTime"),
            @Mapping(source = "update_user", target = "updateUser"),
            @Mapping(source = "update_time", target = "updateTime")
    })
    @Select("select id, author, title, content, create_user, create_time, update_user, update_time from article where id = #{id}")
    public Article getArticleInfoById(@Param("id") String id);

    @Update("update article set content = #{content} where id = #{id}")
    public void editArticle(@Param("id") String id, @Param("content") String content);

    @Update("update article set is_share = 1 where id = #{id}")
    public void editArticleShare(@Param("id") String id);
}
