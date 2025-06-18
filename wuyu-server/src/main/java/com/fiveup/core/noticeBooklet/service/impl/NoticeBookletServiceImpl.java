package com.fiveup.core.noticeBooklet.service.impl;

import com.fiveup.core.common.exception.GlobalExceptionHandler;
import com.fiveup.core.noticeBooklet.domain.NoticeBooklet;
import com.fiveup.core.noticeBooklet.domain.Student;
import com.fiveup.core.noticeBooklet.domain.vo.StudentVO;
import com.fiveup.core.noticeBooklet.mapper.ScoreMapper;
import com.fiveup.core.noticeBooklet.service.CommentGenerationService;
import com.fiveup.core.noticeBooklet.service.NoticeBookletService;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.github.pagehelper.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 通知册服务层
 *
 * @author huangxingcan
 * @date 2025/6/7 17:18
 */
@Service
@Slf4j
public class NoticeBookletServiceImpl implements NoticeBookletService {

    @Resource
    private ScoreMapper scoreMapper;
    @Resource
    private CommentGenerationService commentGenerationService;
    // 存放评价记录的MAP
    private static final ConcurrentHashMap<String, String> remarkMap = new ConcurrentHashMap<>();
    // 分割符
    private static final String DELIMITER = "|";

    /**
     * 获取通知册内容
     *
     * @param studentId 学号
     * @param classId   班级id
     * @param gradeId   年级id
     * @param isRemark  是否通过大模型获取评价
     * @param findKey   搜索关键字
     * @return 通知册
     */
    @Override
    public List<NoticeBooklet> getNoticeBooklet(Integer studentId, Integer classId, Integer gradeId, boolean isRemark, String findKey) {
        List<NoticeBooklet> noticeBookletList = scoreMapper.getNoticeBooklet(studentId, classId, gradeId, findKey);
        if (noticeBookletList.isEmpty()) {
            return Collections.emptyList();
        }
        // 判断是否需要为通知册生成评价
        if (isRemark) {
            // 获取评价数据
            for (NoticeBooklet noticeBooklet : noticeBookletList) {
                // 评价Key
                String remarkKey = noticeBooklet.getStudentId() + DELIMITER + noticeBooklet.getStudentName() + DELIMITER + noticeBooklet.getStudentClassNumber() + DELIMITER + noticeBooklet.getStudentGrade();
                String remark = remarkMap.get(remarkKey);
                if (StringUtil.isNotEmpty(remark)) {
                    noticeBooklet.setRemark(remark);
                    continue;
                }
                // 避免多线程重复调用大模型
                // 使用基于 key 的细粒度锁
                Object lock = remarkMap.computeIfAbsent(remarkKey + "_lock", k -> new Object().toString());

                synchronized (lock) {
                    // 双检锁
                    remark = remarkMap.get(remarkKey);
                    if (StringUtil.isNotEmpty(remark)) {
                        noticeBooklet.setRemark(remark);
                        continue;
                    }
                    try {
                        // 调用评语生成服务
                        String commentResult = commentGenerationService.generateCommentForStudent(noticeBooklet);

                        // 直接将生成的完整内容作为评语
                        noticeBooklet.setRemark(commentResult);
                        remarkMap.put(remarkKey, commentResult);

                    } catch (Exception e) {
                        // 异常处理
                        noticeBooklet.setRemark("评语生成失败：" + e.getMessage());
                        // 记录日志
//                        log.info("生成评价失败：, studentID: {}", noticeBooklet.getStudentId());
                    }
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