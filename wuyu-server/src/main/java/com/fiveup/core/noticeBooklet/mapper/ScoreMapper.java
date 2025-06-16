package com.fiveup.core.noticeBooklet.mapper;

import com.fiveup.core.noticeBooklet.domain.NoticeBooklet;
import com.fiveup.core.noticeBooklet.domain.Student;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author huangxingcan
 * @date 2025/6/8 22:02
 */
@Mapper
public interface ScoreMapper {
    /**
     * 获取学生通知簿
     * @param studentId 学生id
     * @param classId 班级id
     * @param gradeId 年级id
     * @return 通知簿
     */
    List<NoticeBooklet> getNoticeBooklet(Integer studentId, Integer classId, Integer gradeId);

    /**
     * 获取所有学生信息
     *
     * @return 所有学生信息
     */
    List<Student> getAllStudent();
}
