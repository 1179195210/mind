package com.ruoyi.web.controller.mind.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.web.controller.mind.domain.NoteInfo;
import com.ruoyi.web.controller.mind.service.INoteInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 笔记信息
Controller
 * 
 * @author ruoyi
 * @date 2022-08-17
 */
@Controller
@RequestMapping("/system/info")
public class NoteInfoController extends BaseController
{
    private String prefix = "mind/info";

    @Autowired
    private INoteInfoService noteInfoService;

    @RequiresPermissions("system:info:view")
    @GetMapping()
    public String info()
    {
        return prefix + "/info";
    }

    @RequiresPermissions("system:info:view")
    @GetMapping("/ai")
    public String ai()
    {
        return prefix + "/ai";
    }

    /**
     * 查询笔记信息
列表
     */
    @RequiresPermissions("system:info:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(NoteInfo noteInfo)
    {
        startPage();
        List<NoteInfo> list = noteInfoService.selectNoteInfoList(noteInfo);
        return getDataTable(list);
    }


    @PostMapping("/mainlist")
    @ResponseBody
    public TableDataInfo mainlist(NoteInfo noteInfo)
    {
        startPage();
        noteInfo.setIsShare("1");
        List<NoteInfo> list = noteInfoService.selectNoteInfoMainList(noteInfo);
        return getDataTable(list);
    }

    /**
     * 导出笔记信息
列表
     */
    @RequiresPermissions("system:info:export")
    @Log(title = "笔记信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(NoteInfo noteInfo)
    {
        List<NoteInfo> list = noteInfoService.selectNoteInfoList(noteInfo);
        ExcelUtil<NoteInfo> util = new ExcelUtil<NoteInfo>(NoteInfo.class);
        return util.exportExcel(list, "笔记信息数据");
    }

    /**
     * 新增笔记信息

     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存笔记信息

     */
    @RequiresPermissions("system:info:add")
    @Log(title = "笔记信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(NoteInfo noteInfo)
    {
        return toAjax(noteInfoService.insertNoteInfo(noteInfo));
    }

    /**
     * 修改笔记信息

     */
    @RequiresPermissions("system:info:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        NoteInfo noteInfo = noteInfoService.selectNoteInfoById(id);
        mmap.put("noteInfo", noteInfo);
        return prefix + "/edit";
    }

    /**
     * 修改保存笔记信息

     */
    @RequiresPermissions("system:info:edit")
    @Log(title = "笔记信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(NoteInfo noteInfo)
    {
        return toAjax(noteInfoService.updateNoteInfo(noteInfo));
    }

    /**
     * 删除笔记信息

     */
    @RequiresPermissions("system:info:remove")
    @Log(title = "笔记信息", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(noteInfoService.deleteNoteInfoByIds(ids));
    }

    @GetMapping("/goRelation")
    public String goRelation(@RequestParam("id") String id, ModelMap mmap) {
        mmap.put("id", id);
        return prefix + "/goRelation";
    }

    @GetMapping("/addArticle")
    public String addArticle(@RequestParam("id") String id, ModelMap mmap) {
        mmap.put("id", id);

        return prefix + "/addArticle";
    }

    @PostMapping("/share")
    @ResponseBody
    public AjaxResult share(@RequestParam("id") String id) {
        return noteInfoService.share(id);
    }

    @PostMapping("/notShare")
    @ResponseBody
    public AjaxResult notShare(@RequestParam("id") String id) {
        return noteInfoService.notShare(id);
    }
}
