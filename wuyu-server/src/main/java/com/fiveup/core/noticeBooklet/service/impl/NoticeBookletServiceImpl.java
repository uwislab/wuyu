package com.fiveup.core.noticeBooklet.service.impl;

import com.fiveup.core.noticeBooklet.domain.NoticeBooklet;
import com.fiveup.core.noticeBooklet.mapper.ScoreMapper;
import com.fiveup.core.noticeBooklet.service.CommentGenerationService;
import com.fiveup.core.noticeBooklet.service.NoticeBookletService;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * 通知册服务层
 *
 * @author huangxingcan
 * @date 2025/6/7 17:18
 */
@Service
public class NoticeBookletServiceImpl implements NoticeBookletService {

    @Resource
    private ScoreMapper scoreMapper;
    @Resource
    private CommentGenerationService commentGenerationService;

    /**
     * 获取通知册内容
     *
     * @param studentId 学号
     * @param classId   班级id
     * @param gradeId   年级id
     * @return 通知册
     */
    @Override
    public List<NoticeBooklet> getNoticeBooklet(Integer studentId, Integer classId, Integer gradeId) {
        List<NoticeBooklet> noticeBookletList = scoreMapper.getNoticeBooklet(studentId, classId, gradeId);
        // 获取数据
        for (NoticeBooklet noticeBooklet : noticeBookletList) {
            try {
                // 调用评语生成服务
                String studentName = noticeBooklet.getStudentName();
                Long studentIdLong = Long.valueOf(studentId);
                String commentResult = commentGenerationService.generateCommentForStudent(studentName, studentIdLong);

                // 直接将生成的完整内容作为评语
                noticeBooklet.setRemark(commentResult);

            } catch (ApiException | NoApiKeyException | InputRequiredException e) {
                // 异常处理
                noticeBooklet.setRemark("评语生成失败：" + e.getMessage());
            }
        }
        return noticeBookletList;
    }
}