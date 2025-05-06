package com.fiveup.core.questionnaire.domain;


import com.fiveup.core.common.domain.BaseEntity;

/**
 * 选项管理对象 q_options
 * 
 * @author admin
 * @date
 */
public class QOptions extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 选项ID */
    private Long optId;

    /** 题目ID */
    private Long questId;

    /** 选项序号 */
    private String optSeque;

    /** 选项内容 */
    private String optContent;

    // 被选择次数
    private Integer answerCount;

    public Integer getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(Integer answerCount) {
        this.answerCount = answerCount;
    }

    public void setOptId(Long optId)
    {
        this.optId = optId;
    }

    public Long getOptId() 
    {
        return optId;
    }
    public void setQuestId(Long questId) 
    {
        this.questId = questId;
    }

    public Long getQuestId() 
    {
        return questId;
    }
    public void setOptSeque(String optSeque) 
    {
        this.optSeque = optSeque;
    }

    public String getOptSeque() 
    {
        return optSeque;
    }
    public void setOptContent(String optContent) 
    {
        this.optContent = optContent;
    }

    public String getOptContent() 
    {
        return optContent;
    }

}
