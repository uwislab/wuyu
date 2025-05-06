package com.fiveup.core.questionnaire.domain;


import com.fiveup.core.common.domain.BaseEntity;

/**
 * 答题管理对象 q_answer
 * 
 * @author admin
 * @date
 */
public class QAnswer extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long answerId;

    /** 题目ID */
    private Long questId;

    /** 问卷ID */
    private Long paperId;

    /** 题目类型 */
    private Long questionType;

    /** 答题内容 */
    private String answerContent;

    private Long createUserId;

    public void setCreateUserId(Long createUserId)
    {
        this.createUserId = createUserId;
    }

    public Long getCreateUserId()
    {
        return createUserId;
    }

    public void setAnswerId(Long answerId) 
    {
        this.answerId = answerId;
    }

    public Long getAnswerId() 
    {
        return answerId;
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
    public void setQuestionType(Long questionType) 
    {
        this.questionType = questionType;
    }

    public Long getQuestionType() 
    {
        return questionType;
    }
    public void setAnswerContent(String answerContent) 
    {
        this.answerContent = answerContent;
    }

    public String getAnswerContent() 
    {
        return answerContent;
    }

}
