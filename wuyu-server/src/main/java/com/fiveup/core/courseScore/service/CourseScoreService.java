package com.fiveup.core.courseScore.service;

import com.fiveup.core.courseScore.entity.CourseScore;
import com.fiveup.core.courseScore.entity.ResPage;

import java.util.Collection;
import java.util.List;

public interface CourseScoreService {
    /**
     * 成绩录入
     * @param courseScore
     */
    void save(CourseScore courseScore);

    /**
     * 将全部成绩设置为85
     */
    void update85();

    List<CourseScore> getList(String teacherName, int courseType, int testNumber, String courseName);

    /**
     * 条件分页查询
     * @param courseName 课程名称
     * @param courseType 课程类型
     * @param teacherName 教师名称
     * @param studentNum 学生学号
     * @param studentName 学生姓名
     * @param page 页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    ResPage<List<CourseScore>> search(String courseName, Integer courseType, String teacherName, String studentNum, String studentName, Integer page, Integer pageSize);

    /**
     * 根据ids数组删除成绩
     * @param ids
     */
    boolean deleteByIds(String[] ids);

    /**
     * 修改成绩
     * @param courseScore
     */
    void edit(CourseScore courseScore);

    /**
     * 获取所有不同的课程名称
     * @return 课程名称列表
     */
    List<String> getAllDistinctCourseNames();

    /**
     * 获取所有不同的考试次数
     * @return 考试次数列表
     */
    List<Integer> getAllDistinctTestNumbers();

    /**
     * 根据课程名称和考试次数查询成绩
     * @param courseName 课程名称
     * @param testNumber 考试次数
     * @return 成绩列表
     */
    List<CourseScore> getByCourseNameAndTestNumber(String courseName, Integer testNumber);

    /**
     * 获取个人成绩趋势
     * @param courseName 课程名称
     * @param studentName 学生姓名
     * @return 成绩列表
     */
    List<CourseScore> getPersonalTrend(String courseName, String studentName);

    /**
     * 获取学生列表
     * @return 学生成绩列表
     */
    List<CourseScore> getStudentList();

    /**
     * 根据学生学号查询成绩
     * @param studentNum 学生学号
     * @return 成绩列表
     */
    List<CourseScore> getByStudent(String studentNum);
}
