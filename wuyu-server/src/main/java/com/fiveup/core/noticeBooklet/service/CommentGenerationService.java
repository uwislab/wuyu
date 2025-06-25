package com.fiveup.core.noticeBooklet.service;

import com.fiveup.core.fuScore.model.StudentFuScore;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.fiveup.core.noticeBooklet.domain.NoticeBooklet;

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
     * @param noticeBooklet 通知册
     * @return 生成的评语
     * @throws ApiException API调用异常
     * @throws NoApiKeyException 缺少API密钥异常
     * @throws InputRequiredException 缺少输入参数异常
     */
    String generateCommentForStudent(NoticeBooklet noticeBooklet) throws ApiException, NoApiKeyException, InputRequiredException;
}