package com.fiveup.core.courseScore.mapper;

import com.fiveup.core.courseScore.entity.CourseScore;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CourseScoreMapper {

    /**
     * 条件查询
     *
     * @param courseName
     * @param courseType
     * @param teacherName
     * @param studentNum
     * @param studentName
     * @return
     */
    List<CourseScore> findByCondition(String courseName, Integer courseType, String teacherName, String studentNum, String studentName);

    @Insert("insert into course_score (course_name, course_type, test_number, teacher_name, student_num, student_name, input_time, score, remark)" +
            "values " +
            "(#{courseName},#{courseType},#{testNumber},#{teacherName},#{studentNum},#{studentName},#{inputTime},#{score},#{remark})")
    void insert(CourseScore courseScore);

    @Update("update course_score set score = 85")
    void update85();

    /**
     * 分页条件查询
     *
     * @param teacher_name
     * @param course_type
     * @param test_number
     * @param course_name
     * @return
     */
    @Select("select * from course_score where teacher_name=#{teacher_name} and course_type=#{course_type} and test_number=#{test_number} and course_name=#{course_name} and remark=0")
    List<CourseScore> getList(String teacher_name, int course_type, int test_number, String course_name);

    /**
     * 根据ids数组删除成绩
     *
     * @param ids
     * @return
     */
    int deleteByIds(@Param("ids") String[] ids);

    /**
     * 修改成绩
     *
     * @param courseScore
     */
    @Update("UPDATE course_score SET " +
            "course_name = #{courseName}, " +
            "course_type = #{courseType}, " +
            "test_number = #{testNumber}, " +
            "teacher_name = #{teacherName}, " +
            "student_num = #{studentNum}, " +
            "student_name = #{studentName}, " +
            "input_time = #{inputTime}, " +
            "score = #{score}, " +
            "remark = #{remark} " +
            "WHERE id = #{id}")
    void edit(CourseScore courseScore);


    @Select("SELECT " +
            "DISTINCT" +
            " course_name" +
            " FROM course_score")
    List<String> getAllDistinctCourseNames();

    @Select("SELECT " +
            "DISTINCT " +
            "test_number " +
            "FROM course_score")
    List<Integer> getAllDistinctTestNumbers();

    @Select("SELECT" +
            " * " +
            "FROM " +
            "course_score" +
            " WHERE course_name = #{courseName}" +
            " AND test_number = #{testNumber}")
    List<CourseScore> getByCourseNameAndTestNumber(@Param("courseName") String courseName, @Param("testNumber") Integer testNumber);

}
