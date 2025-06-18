package com.fiveup.core.noticeBooklet.service;

import com.fiveup.core.noticeBooklet.domain.NoticeBooklet;
import com.fiveup.core.noticeBooklet.domain.Student;
import com.fiveup.core.noticeBooklet.domain.vo.StudentVO;

import java.util.List;

/**
 * 通知册服务层
 *
 * @author huangxingcan
 * @date 2025/6/7 17:18
 */
public interface NoticeBookletService {

    /**
     * 获取通知册内容
     * @param studentId 学号
     * @param classId 班级id
     * @param gradeId 年级id
     * @param isRemark 是否通过大模型获取评价
     * @param findKey 搜索关键字
     * @return 通知册
     */
    List<NoticeBooklet> getNoticeBooklet(Integer studentId, Integer classId, Integer gradeId, boolean isRemark, String findKey);

    /**
     * 获取所有学生学号、(班级,年级)、年级
     *
     * @return 所有学生信息
     */
    StudentVO getAllStudent();
}
