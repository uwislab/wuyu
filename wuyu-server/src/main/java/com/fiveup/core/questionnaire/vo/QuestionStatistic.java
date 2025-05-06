package com.fiveup.core.questionnaire.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionStatistic {
    private Integer id;
    private Integer paperId;
    private Integer type;
    private String title;
    private Integer filledInNum;    //此题填写人数
    private List<OptionStatistic> optionStatistics;   //选择题所有属性 简答题此属性为null
    private List<AnswerVO> answerVOList;    //简单题的所有answer 选择题此属性为null

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getFilledInNum() {
        return filledInNum;
    }

    public void setFilledInNum(Integer filledInNum) {
        this.filledInNum = filledInNum;
    }

    public List<OptionStatistic> getOptionStatistics() {
        return optionStatistics;
    }

    public void setOptionStatistics(List<OptionStatistic> optionStatistics) {
        this.optionStatistics = optionStatistics;
    }

    public List<AnswerVO> getAnswerVOList() {
        return answerVOList;
    }

    public void setAnswerVOList(List<AnswerVO> answerVOList) {
        this.answerVOList = answerVOList;
    }
}
