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

    List<CourseScore> getList(String teacher_name,int course_type,int test_number,String course_name);

    /**
     * 条件分页查询
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
     * @param ids
     */
    boolean deleteByIds(String[] ids);

    /**
     * 修改成绩
     * @param courseScore
     */
    void edit(CourseScore courseScore);
}
