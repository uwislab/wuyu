package com.fiveup.core.noticeBooklet.service.impl;

import com.fiveup.core.noticeBooklet.domain.NoticeBooklet;
import com.fiveup.core.noticeBooklet.mapper.ScoreMapper;
import com.fiveup.core.noticeBooklet.service.CommentGenerationService;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.fiveup.core.noticeBooklet.utils.AIUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 评语生成服务实现
 *
 * @author huangyuhao
 * @date 2025/6/8
 */
@Service
public class CommentGenerationServiceImpl implements CommentGenerationService {

    @Autowired
    private ScoreMapper scoreMapper;

    @Override
    public String generateCommentForStudent(String studentName, Long studentId) throws ApiException, NoApiKeyException, InputRequiredException {
        Integer studentIdAsInt = studentId.intValue();
        // 直接从 ScoreMapper 获取通知册信息
        List<NoticeBooklet> noticeBooklets = scoreMapper.getNoticeBooklet(studentIdAsInt, null, null);
        if (noticeBooklets.isEmpty()) {
            return "未找到该学生的成绩信息。";
        }
        NoticeBooklet noticeBooklet = noticeBooklets.get(0);

        // 提取五育成绩
        double[] scores = {
                noticeBooklet.getSDeyu() == null ? 0.0 : noticeBooklet.getSDeyu(),
                noticeBooklet.getSZhiyu() == null ? 0.0 : noticeBooklet.getSZhiyu(),
                noticeBooklet.getSTiyu() == null ? 0.0 : noticeBooklet.getSTiyu(),
                noticeBooklet.getSMeiyu() == null ? 0.0 : noticeBooklet.getSMeiyu(),
                noticeBooklet.getSLaoyu() == null ? 0.0 : noticeBooklet.getSLaoyu()
        };

        // 调用生成评语的方法
        return AIUtil.generateComment(studentName, scores);
    }
}