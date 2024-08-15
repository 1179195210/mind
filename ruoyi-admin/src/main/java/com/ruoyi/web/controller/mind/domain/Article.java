package com.ruoyi.web.controller.mind.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Article {


    private String Id;

    private String author;

    private String title;

    private String content;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

    private String isShare;
}
