package com.fiveup.core.noticeBooklet.service;

import com.fiveup.core.fuScore.model.StudentFuScore;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;

import java.util.List;

/**
 * 评语生成服务接口
 *
 * @author huangxingcan
 * @date 2025/6/8
 */
public interface CommentGenerationService {

    /**
     * 为学生生成评语
     * @param studentName 学生姓名
     * @param studentId 学生ID
     * @return 生成的评语
     * @throws ApiException API调用异常
     * @throws NoApiKeyException 缺少API密钥异常
     * @throws InputRequiredException 缺少输入参数异常
     */
    String generateCommentForStudent(String studentName, Long studentId) throws ApiException, NoApiKeyException, InputRequiredException;
}