package com.fiveup.core.noticeBooklet.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.fiveup.core.common.exception.ApiException;
import com.fiveup.core.common.exception.GlobalExceptionHandler;
import com.fiveup.core.noticeBooklet.domain.*;
import com.fiveup.core.noticeBooklet.domain.vo.StudentVO;
import com.fiveup.core.noticeBooklet.mapper.ScoreMapper;
import com.fiveup.core.noticeBooklet.service.CommentGenerationService;
import com.fiveup.core.noticeBooklet.service.NoticeBookletService;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.fiveup.core.overallOperation.domain.StudentScore;
import com.github.pagehelper.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
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
            // 添加学号
            studentIDSet.add(student.getStudentId());
            // 添加班级
            classSet.add(new int[]{student.getStudentClassNumber(), student.getStudentGrade()});
            // 添加年级
            gradeSet.add(student.getStudentGrade());
        }
        result.setStudentIds(studentIDSet);
        result.setClassNames(classSet);
        result.setGrades(gradeSet);
        return result;
    }


    /**
     * 修改通知册内容
     *
     * @param noticeBooklet 通知册内容
     * @return 修改成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modifyNoticeBooklet(NoticeBooklet noticeBooklet) {
        // 检查参数
        boolean isEmpty = noticeBooklet.getStudentId() == null
                || StringUtil.isEmpty(noticeBooklet.getStudentName())
                || noticeBooklet.getStudentClassNumber() == null
                || noticeBooklet.getStudentGrade() == null;
        if (isEmpty) {
            throw new ApiException("学号、学生姓名、班级、年级等必要参数不能为空");
        }
        // 获取该同学的信息
        NoticeBooklet noticeBookletOld = scoreMapper.getNoticeBookletByStudentId(noticeBooklet.getStudentId());
        if (noticeBookletOld == null) {
            throw new ApiException("该同学不存在");
        }
        // 不能同时修改一个同学
        // 使用锁来控制, 细粒度锁
        Object lock = remarkMap.computeIfAbsent(noticeBooklet.getStudentId() + "_lock", k -> new Object().toString());
        // 修改通知册内容
        synchronized (lock) {
            // 去除该同学的评价
            remarkMap.remove(noticeBooklet.getStudentId() + DELIMITER + noticeBookletOld.getStudentName() + DELIMITER + noticeBookletOld.getStudentClassNumber() + DELIMITER + noticeBookletOld.getStudentGrade());
            // 获取该学生成绩，判断新增还是修改
            DiStudentScore diStudentScore = scoreMapper.getStudentScore(noticeBooklet.getStudentId());
            DiStudentScore studentScore = BeanUtil.copyProperties(noticeBooklet, DiStudentScore.class);
            if (diStudentScore == null || diStudentScore.getId() == null) {
                // 新增
                scoreMapper.addStudentScore(studentScore);
            } else {
                // 修改
                scoreMapper.modifyStudentScoreByStudentId(diStudentScore.getId(), studentScore);
            }
            DiStudentPlan diStudentPlan = scoreMapper.getStudentPlan(noticeBooklet.getStudentId());
            DiStudentPlan studentPlan = BeanUtil.copyProperties(noticeBooklet, DiStudentPlan.class);
            studentPlan.setPId(noticeBooklet.getStudentId());
            if (diStudentPlan == null) {
                // 新增
                scoreMapper.addStudentPlan(studentPlan);
            } else {
                // 修改
                scoreMapper.modifyStudentPlan(studentPlan);
            }
            DiStudentPlanComment diStudentPlanComment = scoreMapper.getStudentPlanComment(noticeBooklet.getStudentId());
            DiStudentPlanComment studentPlanComment = new DiStudentPlanComment();
            studentPlanComment.setPId(noticeBooklet.getStudentId());
            studentPlanComment.setComment(noticeBooklet.getComment());
            if (diStudentPlanComment == null || diStudentPlanComment.getId() == null) {
                // 新增
                studentPlanComment.setCreateTime(LocalDate.now());
                studentPlanComment.setUpdateTime(LocalDate.now());
                scoreMapper.addStudentPlanComment(studentPlanComment);
            } else {
                // 修改
                studentPlanComment.setId(diStudentPlanComment.getId());
                studentPlanComment.setUpdateTime(LocalDate.now());
                scoreMapper.modifyStudentPlanComment(studentPlanComment);
            }
        }
//        log.info("修改通知册内容成功：, studentID: {}", noticeBooklet.getStudentId());
    }
}