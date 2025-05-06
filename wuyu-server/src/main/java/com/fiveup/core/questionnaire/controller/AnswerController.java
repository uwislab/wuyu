package com.fiveup.core.questionnaire.controller;

import com.fiveup.core.questionnaire.service.AnswerService;
import com.fiveup.core.questionnaire.vo.AnswerVO;
import com.fiveup.core.questionnaire.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Copyright (C), 2015-2021, XXX有限公司
 * @ClassName: AnswerController
 * @Author: 作者姓名(一般是写姓名的拼音)
 * @E-mail: 邮箱
 * @Date: 2021/4/26 12:19
 * @Version: V1.0
 * @Description: 对该类的描述
 */
@RestController
@RequestMapping("/api/answer")
public class AnswerController {

    @Autowired
    AnswerService answerService;

    @PostMapping("/addAnswers")
    public ResponseVO addAnswers(@RequestBody List<AnswerVO> answerVOList){
        return answerService.addAnswers(answerVOList);
    }

    @GetMapping("/{paperId}/reviewAnswers")
    public ResponseVO reviewAnswers(@PathVariable Integer paperId){
        return answerService.reviewAnswers(paperId);
    }
}
