package com.fiveup.core.questionnaire.service;

import com.fiveup.core.questionnaire.vo.AnswerVO;
import com.fiveup.core.questionnaire.vo.ResponseVO;

import java.util.List;

/**
 * @Copyright (C), 2015-2021, XXX有限公司
 * @ClassName: AnswerService
 * @Author: 作者姓名(一般是写姓名的拼音)
 * @E-mail: 邮箱
 * @Date: 2021/4/26 12:22
 * @Version: V1.0
 * @Description: 对该类的描述
 */
public interface AnswerService {
    ResponseVO addAnswers(List<AnswerVO> answerVOList);

    /**
     * 根据paperId，搜索每条相关的回答
     * @param paperId
     * @return
     */
    ResponseVO reviewAnswers(int paperId);
}
