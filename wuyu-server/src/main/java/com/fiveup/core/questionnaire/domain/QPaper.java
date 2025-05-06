package com.fiveup.core.questionnaire.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fiveup.core.common.domain.BaseEntity;

/**
 * 问卷信息对象 q_paper
 * 
 * @author
 * @date
 */
public class QPaper extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long paperId;

    /** 问卷标题 */
    private String paperTitle;

    /** 问卷描述 */
    private String description;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")

    private Date startTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date endTime;

    /** 问卷状态 */
    private String paperStatus;

    /** 问卷类型 */
    private String paperType;


    public void setPaperId(Long paperId) 
    {
        this.paperId = paperId;
    }

    public Long getPaperId() 
    {
        return paperId;
    }
    public void setPaperTitle(String paperTitle) 
    {
        this.paperTitle = paperTitle;
    }

    public String getPaperTitle() 
    {
        return paperTitle;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }
    public void setStartTime(Date startTime) 
    {
        this.startTime = startTime;
    }

    public Date getStartTime() 
    {
        return startTime;
    }
    public void setEndTime(Date endTime) 
    {
        this.endTime = endTime;
    }

    public Date getEndTime() 
    {
        return endTime;
    }
    public void setPaperStatus(String paperStatus) 
    {
        this.paperStatus = paperStatus;
    }

    public String getPaperStatus() 
    {
        return paperStatus;
    }
    public void setPaperType(String paperType) 
    {
        this.paperType = paperType;
    }

    public String getPaperType() 
    {
        return paperType;
    }

}
