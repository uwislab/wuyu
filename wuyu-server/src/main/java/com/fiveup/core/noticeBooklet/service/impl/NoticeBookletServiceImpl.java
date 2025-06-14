package com.fiveup.core.noticeBooklet.service.impl;

import com.fiveup.core.noticeBooklet.domain.NoticeBooklet;
import com.fiveup.core.noticeBooklet.domain.Student;
import com.fiveup.core.noticeBooklet.domain.vo.StudentVO;
import com.fiveup.core.noticeBooklet.mapper.ScoreMapper;
import com.fiveup.core.noticeBooklet.service.CommentGenerationService;
import com.fiveup.core.noticeBooklet.service.NoticeBookletService;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

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
     * @param isRemark  是否需要评语
     * @return 通知册
     */
    @Override
    public List<NoticeBooklet> getNoticeBooklet(Integer studentId, Integer classId, Integer gradeId, boolean isRemark) {
        List<NoticeBooklet> noticeBookletList = scoreMapper.getNoticeBooklet(studentId, classId, gradeId);
        if (isRemark) {
            // 获取评价数据
            for (NoticeBooklet noticeBooklet : noticeBookletList) {
                try {
                    // 调用评语生成服务
                    String commentResult = commentGenerationService.generateCommentForStudent(noticeBooklet);

                    // 直接将生成的完整内容作为评语
                    noticeBooklet.setRemark(commentResult);

                } catch (ApiException | NoApiKeyException | InputRequiredException e) {
                    // 异常处理
                    noticeBooklet.setRemark("评语生成失败：" + e.getMessage());
                }
            }
        }
        return noticeBookletList;
    }

    /**
     * 获取所有学生学号、(班级,年级)、年级
     *
     * @return 所有学生信息
     */
    @Override
    public StudentVO getAllStudent() {
        List<Student> studentList = scoreMapper.getAllStudent();
        StudentVO result = new StudentVO();
        Set<Integer> studentIDSet = new HashSet<>();
        Set<int[]> classSet = new HashSet<>();
        Set<Integer> gradeSet = new HashSet<>();
        for (Student student : studentList) {
            studentIDSet.add(student.getStudentId());
            classSet.add(new int[]{student.getStudentClassNumber(), student.getStudentGrade()});
            gradeSet.add(student.getStudentGrade());
        }
        result.setStudentIds(studentIDSet);
        result.setClassNames(classSet);
        result.setGrades(gradeSet);
        return result;
    }
}