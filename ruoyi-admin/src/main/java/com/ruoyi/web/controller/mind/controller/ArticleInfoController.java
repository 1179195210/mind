package com.ruoyi.web.controller.mind.controller;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.web.controller.mind.service.impl.ArticleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

/**
 * 笔记信息
 * Controller
 */
@Controller
@RequestMapping("/articleInfo")
public class ArticleInfoController extends BaseController {

    @Autowired
    private ArticleServiceImpl articleService;

    @Value("${imagePath}")
    private String imagePath;

    private String prefix = "mind/info";

    @GetMapping("/addArticle")
    public String addArticle(@RequestParam("id") String id, @RequestParam("name") String name, ModelMap mmap) {
        mmap.put("id", id);
        mmap.put("name", name);
        System.out.println(name);
        String content = articleService.addArticle(id, name);
        mmap.put("content", content);
        return prefix + "/addArticle";
    }

    @PostMapping("/addArticle")
    public String addArticlePost(@RequestParam("id") String id, @RequestParam("name") String name, ModelMap mmap) {
        mmap.put("id", id);
        mmap.put("name", name);
        System.out.println(name);
        String content = articleService.addArticle(id, name);
        mmap.put("content", content);
        return prefix + "/addArticle";
    }

    @PostMapping("/editArticle")
    @ResponseBody
    public AjaxResult editArticle(@RequestParam("id") String id, @RequestParam("content") String content) {
        return articleService.editArticle(id, content);
    }


    @PostMapping("/share")
    @ResponseBody
    public AjaxResult share(@RequestParam("id") String id) {
        return articleService.share(id);
    }




    /**
     * 上传图片
     *
     * @param file
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping("/file/upload")
    @ResponseBody
    public JSONObject fileUpload(@RequestParam(value = "editormd-image-file", required = true) MultipartFile file, HttpServletRequest request) throws IOException {
        // =====上传路径保存设置======
        // 获得SpringBoot当前项目的路径：System.getProperty("user.dir")
        String path = imagePath;

        // 按照年份、月份进行分类：
        LocalDate date = LocalDate.now();
        // 路径中不要加中文，否则在路径转码的情况下，回显会有问题
        // String ym = date.getYear() + "\\" + date.getMonthValue() + "月";
        String ym = date.getYear() + "Y" + date.getMonthValue() + "M";
        path = path + "/articleInfo/" + ym;
        File realPath = new File(path);
        if (!realPath.exists()) {
            // 这里有个坑；使用mkdirs，不是mkdir；因为mkdir只能创建一级目录，mkdirs可以创建多级目录
            realPath.mkdirs();
        }

        // 上传文件地址
        System.out.println("上传文件保存地址：" + realPath);

        // 文件名
        String filename = file.getOriginalFilename();


        // 文件类型
        String fileType = filename.substring(filename.lastIndexOf(".")).toLowerCase();

        // 随机文件名：使用uuid;
        filename = "img-" + UUID.randomUUID().toString().replaceAll("-", "") + fileType;

        // 通过CommonsMultipartFile的方法直接写文件
        file.transferTo(new File(realPath + "/" + filename));
        BufferedImage image = ImageIO.read(new FileInputStream(new File(realPath + "/" + filename)));
        int width = image.getWidth();
        int height = image.getHeight();
        // 给editormd进行回调
        JSONObject res = new JSONObject();
        res.put("url", realPath.toString().split("/")[realPath.toString().split("/").length - 1] + "/" + filename);
        res.put("success", 1);
        res.put("message", "upload success!");
        res.put("height", height);

        return res;
    }
}
