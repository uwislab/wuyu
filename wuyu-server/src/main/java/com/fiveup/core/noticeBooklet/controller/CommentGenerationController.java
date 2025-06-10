package com.fiveup.core.noticeBooklet.controller;

import com.fiveup.core.noticeBooklet.service.CommentGenerationService;
import com.fiveup.core.management.common.CommonResponse;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment")
public class CommentGenerationController {

    @Autowired
    private CommentGenerationService commentGenerationService;

    @GetMapping("/generate")
    public CommonResponse<String> generateComment(@RequestParam String studentName, @RequestParam Long studentId) {
        try {
            String comment = commentGenerationService.generateCommentForStudent(studentName, studentId);
            return CommonResponse.ok(comment);
        } catch (ApiException | NoApiKeyException | InputRequiredException e) {
            return CommonResponse.fail(1002, "生成评语失败：" + e.getMessage());
        }
    }
}