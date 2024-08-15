package com.ruoyi.web.controller.mind.mapper;

import com.ruoyi.web.controller.mind.domain.NoteInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 笔记信息
Mapper接口
 * 
 * @author ruoyi
 * @date 2022-08-17
 */
@Mapper
public interface NoteInfoMapper 
{
    /**
     * 查询笔记信息

     * 
     * @param id 笔记信息
主键
     * @return 笔记信息

     */
    public NoteInfo selectNoteInfoById(Long id);


    /**
     * 查询笔记信息
列表
     * 
     * @param noteInfo 笔记信息

     * @return 笔记信息
集合
     */
    public List<NoteInfo> selectNoteInfoList(NoteInfo noteInfo);

    public List<NoteInfo> selectNoteInfoMainList(NoteInfo noteInfo);

    /**
     * 新增笔记信息

     * 
     * @param noteInfo 笔记信息

     * @return 结果
     */
    public int insertNoteInfo(NoteInfo noteInfo);

    /**
     * 修改笔记信息

     * 
     * @param noteInfo 笔记信息

     * @return 结果
     */
    public int updateNoteInfo(NoteInfo noteInfo);

    /**
     * 删除笔记信息

     * 
     * @param id 笔记信息
主键
     * @return 结果
     */
    public int deleteNoteInfoById(Long id);

    /**
     * 批量删除笔记信息

     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteNoteInfoByIds(String[] ids);

    @Update("update note_info set is_share = 1 where id = #{id}")
    public void editNoteShare(@Param("id") String id);

    @Update("update note_info set is_share = 0 where id = #{id}")
    public void editNoteNotShare(@Param("id") String id);
}
