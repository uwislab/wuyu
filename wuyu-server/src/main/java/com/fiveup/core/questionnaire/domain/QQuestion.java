package com.fiveup.core.questionnaire.domain;


import com.fiveup.core.common.domain.BaseEntity;

import java.util.List;

/**
 * 题目管理对象 q_question
 * 
 * @author admin
 * @date
 */
public class QQuestion extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long questId;

    /** 问卷ID */
    private Long paperId;

    /** 题目类型 */
    private String questType;

    /** 题目标题 */
    private String questTitle;

    /**
     * 试卷状态
     */



    // 选项对象
    private List<QOptions> qOptionsList;

    public List<QOptions> getqOptionsList() {
        return qOptionsList;
    }

    public void setqOptionsList(List<QOptions> qOptionsList) {
        this.qOptionsList = qOptionsList;
    }

    public void setQuestId(Long questId)
    {
        this.questId = questId;
    }

    public Long getQuestId() 
    {
        return questId;
    }
    public void setPaperId(Long paperId) 
    {
        this.paperId = paperId;
    }

    public Long getPaperId() 
    {
        return paperId;
    }
    public void setQuestType(String questType) 
    {
        this.questType = questType;
    }

    public String getQuestType() 
    {
        return questType;
    }
    public void setQuestTitle(String questTitle) 
    {
        this.questTitle = questTitle;
    }

    public String getQuestTitle() 
    {
        return questTitle;
    }
}
