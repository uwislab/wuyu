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
     * @param findKey 搜索关键字
     * @return 通知簿
     */
    List<NoticeBooklet> getNoticeBooklet(Integer studentId, Integer classId, Integer gradeId, String findKey);

    /**
     * 获取所有学生信息
     *
     * @return 所有学生信息
     */
    List<Student> getAllStudent();

    /**
     * 获取学生成绩
     * @param studentId 学生id
     * @return 学生成绩
     */
    NoticeBooklet getNoticeBookletByStudentId(Integer studentId);

    /**
     * 删除学生成绩
     * @param studentId 学生id
     */
    void deleteNoticeBooklet(Integer studentId);

    /**
     * 获取班级通知簿
     * @param classNum 班级编号
     * @param gradeId 年级id
     * @return 班级通知簿
     */
    List<NoticeBooklet> getNoticeBookletByClassNumAndGradeId(Integer classNum, Integer gradeId);

    /**
     * 获取年级通知簿
     * @param gradeId 年级id
     * @return 年级通知簿
     */
    List<NoticeBooklet> getNoticeBookletByGradeId(Integer gradeId);
}
