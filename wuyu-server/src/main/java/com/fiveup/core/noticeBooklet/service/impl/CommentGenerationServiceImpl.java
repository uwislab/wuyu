package com.fiveup.core.noticeBooklet.service.impl;

import com.fiveup.core.fuScore.model.StudentFuScore;
import com.fiveup.core.fuScore.service.StudentFuScoreService;
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
    private StudentFuScoreService studentFuScoreService;

    @Override
    public String generateCommentForStudent(String studentName, Long studentId) throws ApiException, NoApiKeyException, InputRequiredException {
        int studentIdAsInt = studentId.intValue();
        // 获取学生的五育成绩
        List<StudentFuScore> studentFuScores = studentFuScoreService.getStudentsFuScore(studentName, studentIdAsInt);
        if (studentFuScores.isEmpty()) {
            return "未找到该学生的成绩信息。";
        }
        StudentFuScore score = studentFuScores.get(0);

        // 提取五育成绩
        double[] scores = {
                score.getMoralityScore(),
                score.getIntelligenceScore(),
                score.getPhysicalScore(),
                score.getAestheticScore(),
                score.getLabourScore()
        };

        // 调用生成评语的方法
        return AIUtil.generateComment(studentName, scores);
    }
}
