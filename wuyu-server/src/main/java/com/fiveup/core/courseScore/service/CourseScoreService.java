package com.fiveup.core.courseScore.service;

import com.fiveup.core.courseScore.entity.CourseScore;
import com.fiveup.core.courseScore.entity.ResPage;

import java.util.Collection;
import java.util.List;

public interface CourseScoreService {
    /**
     * 成绩录入
     *
     * @param courseScore
     */
    void save(CourseScore courseScore);

    /**
     * 将全部成绩设置为85
     */
    void update85();

    List<CourseScore> getList(String teacher_name, int course_type, int test_number, String course_name);

    /**
     * 条件分页查询
     *
     * @param courseName
     * @param courseType
     * @param teacherName
     * @param studentNum
     * @param studentName
     * @param page
     * @param pageSize
     * @return
     */
    ResPage<List<CourseScore>> search(String courseName, Integer courseType, String teacherName, String studentNum, String studentName, Integer page, Integer pageSize);

    /**
     * 根据ids数组删除成绩
     *
     * @param ids
     */
    boolean deleteByIds(String[] ids);

    /**
     * 修改成绩
     *
     * @param courseScore
     */
    void edit(CourseScore courseScore);


    /**
     * 获取所有课程名称
     *
     * @return
     */
    List<String> getAllDistinctCourseNames();

    /**
     * 获取所有不同的考试序号
     *
     * @return
     */
    List<Integer> getAllDistinctTestNumbers();


    /**
     * 根据课程名称和考试序号获取成绩
     *
     * @param courseName
     * @param testNumber
     * @return
     */
    List<CourseScore> getByCourseNameAndTestNumber(String courseName, Integer testNumber);


    /**
     * 获取学生在某门课程中的历次考试成绩
     *
     * @param courseName  课程名称
     * @param studentName 学生姓名
     * @return 历次考试成绩列表
     */
    List<CourseScore> getPersonalTrend(String courseName, String studentName);


    /**
     * 获取所有学生信息（去重）
     *
     * @return 学生列表
     */
    List<CourseScore> getStudentList();

    /**
     * 根据学生学号查询所有课程成绩
     *
     * @param studentNum 学生学号
     * @return 成绩列表
     */
    List<CourseScore> getByStudent(String studentNum);

}
