package com.ruoyi.web.controller.mind.service.impl;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.system.domain.SysConfig;
import com.ruoyi.web.controller.mind.domain.Article;
import com.ruoyi.web.controller.mind.domain.NoteInfo;
import com.ruoyi.web.controller.mind.mapper.ArticleInfoMapper;
import com.ruoyi.web.controller.mind.mapper.NoteInfoMapper;
import com.ruoyi.web.controller.mind.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 笔记信息
Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-08-17
 */
@Service
public class ArticleServiceImpl implements IArticleService
{
    @Autowired
    private ArticleInfoMapper articleInfoMapper;

    @Autowired
    private NoteInfoMapper noteInfoMapper;


    @Override
    public String addArticle(String id, String name) {
        Article articleSource = articleInfoMapper.getArticleInfoById(id.split(",")[0]);
        if (articleSource == null) {
            String author = ShiroUtils.getSysUser().getUserName();
            String createUser = author;
            String updateUser = author;
            String title = name;
            String content = "";
            Date updateTime = new Date();
            Date createTime = new Date();
            Article article = new Article();
            article.setId(id.split(",")[0]);
            article.setAuthor(author);
            article.setContent(content);
            article.setTitle(title);
            article.setCreateUser(createUser);
            article.setCreateTime(createTime);
            article.setUpdateUser(updateUser);
            article.setUpdateTime(updateTime);
            articleInfoMapper.addArticle(article);
            return content;
        } else {
            return articleSource.getContent();
        }

    }

    @Override
    public AjaxResult editArticle(String id, String content) {
        String username = ShiroUtils.getSysUser().getUserName();
        try {
            NoteInfo article = noteInfoMapper.selectNoteInfoById(Long.valueOf(id.split(",")[0]));
            if (article.getCreateUser().equals(username)) {
                articleInfoMapper.editArticle(id.split(",")[0], content);
                return AjaxResult.success("保存成功");
            } else {
                return AjaxResult.error("非文章原作者不可更改文档");
            }
        } catch (NumberFormatException exception) {

            Article article = articleInfoMapper.getArticleInfoById(id.split(",")[0]);
            if (article.getCreateUser().equals(username)) {
                articleInfoMapper.editArticle(id.split(",")[0], content);
                return AjaxResult.success("保存成功");
            } else {
                return AjaxResult.error("非文章原作者不可更改文档");
            }

        } catch (Exception e) {
            return AjaxResult.error("保存失败，请刷新后重试");
        }
    }

    @Override
    public AjaxResult share(String id) {
        Article articleSource = articleInfoMapper.getArticleInfoById(id);
        if (articleSource.getIsShare().equals("0")) {
            articleInfoMapper.editArticleShare(id);
            return AjaxResult.success("保存成功");

        } else {
            return AjaxResult.error("已分享，无法重复分享。");
        }

    }
}
