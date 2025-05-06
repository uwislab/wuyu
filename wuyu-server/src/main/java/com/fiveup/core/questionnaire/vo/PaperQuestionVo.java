package com.fiveup.core.questionnaire.vo;

import com.fiveup.core.questionnaire.domain.QQuestion;
import lombok.Data;

import java.util.List;

@Data
public class PaperQuestionVo {
    private List<QQuestion> qQuestions;
    private  PaperVO paperVO;

    public List<QQuestion> getqQuestions() {
        return qQuestions;
    }

    public void setqQuestions(List<QQuestion> qQuestions) {
        this.qQuestions = qQuestions;
    }

    public PaperVO getPaperVO() {
        return paperVO;
    }

    public void setPaperVO(PaperVO paperVO) {
        this.paperVO = paperVO;
    }

}
