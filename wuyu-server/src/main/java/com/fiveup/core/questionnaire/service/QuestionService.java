package com.fiveup.core.questionnaire.service;


import com.fiveup.core.questionnaire.vo.QuestionVO;
import com.fiveup.core.questionnaire.vo.ResponseVO;

public interface QuestionService {

    ResponseVO addQuestion(Integer paperId);

    ResponseVO updateQuestion(QuestionVO questionVO);

    ResponseVO deleteQuestion(Integer questionId);
}
