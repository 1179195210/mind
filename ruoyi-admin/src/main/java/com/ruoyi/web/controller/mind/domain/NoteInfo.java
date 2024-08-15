package com.ruoyi.web.controller.mind.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 笔记信息
对象 note_info
 * 
 * @date 2022-08-17
 */
public class NoteInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 笔记名称 */
    @Excel(name = "笔记名称")
    private String noteName;

    /** 笔记类型 */
    @Excel(name = "笔记类型")
    private String noteType;

    private String noteTypeName;
    /** 作者 */
    @Excel(name = "作者")
    private String createUser;

    public String getIsShare() {
        return isShare;
    }

    public void setIsShare(String isShare) {
        this.isShare = isShare;
    }

    private String isShare;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setNoteName(String noteName) 
    {
        this.noteName = noteName;
    }

    public String getNoteName() 
    {
        return noteName;
    }
    public void setNoteType(String noteType) 
    {
        if (noteType.equals("mind")) {
            this.noteTypeName = "思维导图";
        }
        if (noteType.equals("word")) {
            this.noteTypeName = "文档";
        }
        this.noteType = noteType;
    }
    public String getNoteTypeName()
    {

        return noteTypeName;
    }

    public String getNoteType() 
    {

        return noteType;
    }
    public void setCreateUser(String createUser) 
    {
        this.createUser = createUser;
    }

    public String getCreateUser() 
    {
        return createUser;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("noteName", getNoteName())
            .append("noteType", getNoteType())
            .append("remark", getRemark())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("createUser", getCreateUser())
            .append("isShare", getIsShare())
            .toString();
    }
}
