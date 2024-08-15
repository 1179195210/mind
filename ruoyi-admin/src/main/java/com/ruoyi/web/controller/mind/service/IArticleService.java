package com.ruoyi.web.controller.mind.service;

import com.ruoyi.common.core.domain.AjaxResult;


/**
 * 笔记信息
Service接口
 * 
 * @author ruoyi
 * @date 2022-08-17
 */
public interface IArticleService
{

    public String addArticle(String id, String name);

    public AjaxResult editArticle(String id, String content);

    public AjaxResult share(String id);

}
