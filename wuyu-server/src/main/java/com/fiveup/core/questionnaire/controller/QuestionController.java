package com.fiveup.core.questionnaire.controller;


import com.fiveup.core.questionnaire.service.QuestionService;
import com.fiveup.core.questionnaire.vo.QuestionVO;
import com.fiveup.core.questionnaire.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("/{paperId}/addQuestion")
    public ResponseVO addQuestion(@PathVariable Integer paperId){
        return questionService.addQuestion(paperId);
    }

    @PostMapping("/updateQuestion")
    public ResponseVO updateQuestion(@RequestBody QuestionVO questionVO){
        return questionService.updateQuestion(questionVO);
    }

    @GetMapping("/{questionId}/deleteQuestion")
    public ResponseVO deleteQuestion(@PathVariable Integer questionId){
        return questionService.deleteQuestion(questionId);
    }
}
