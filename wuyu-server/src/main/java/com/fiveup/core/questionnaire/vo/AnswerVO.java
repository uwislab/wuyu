package com.fiveup.core.questionnaire.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Copyright (C), 2015-2021, XXX有限公司
 * @ClassName: AnswerVO
 * @Author: 作者姓名(一般是写姓名的拼音)
 * @E-mail: 邮箱
 * @Date: 2021/4/26 12:23
 * @Version: V1.0
 * @Description: 对该类的描述
 */
@Data
@AllArgsConstructor
public class AnswerVO {

    private Integer id;

    private Integer paperId;

    private Integer questionId;

    private Integer questionType;

    private String createTime;

    private String answerContent;

    private String user_uuid; // 前端不知道，后端生成的，用于添加答案时候用

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPaperId() {
        return paperId;
    }

    public void setPaperId(Integer paperId) {
        this.paperId = paperId;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    public String getUser_uuid() {
        return user_uuid;
    }

    public void setUser_uuid(String user_uuid) {
        this.user_uuid = user_uuid;
    }
}
