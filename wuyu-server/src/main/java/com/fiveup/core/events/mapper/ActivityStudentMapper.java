package com.fiveup.core.events.mapper;

import com.fiveup.core.events.model.ActivityStudent;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ActivityStudentMapper {

    @Insert("insert into activity_student(activity_id, student_num, stu_act_score) " +
            "values (#{activityId}, #{studentNum}, #{stuActScore})")
    boolean insertOne(ActivityStudent activityStudent);

    @Update("update activity_student set stu_act_score = #{stuActScore} " +
            "where activity_id = #{activityId} and student_num = #{studentNum} and deleted = 0")
    boolean updateOne(ActivityStudent activityStudent);

    @Select("select id, activity_id, student_num, stu_act_score, deleted " +
            "from activity_student " +
            "where activity_id = #{actId} and student_num = #{studentNum} and deleted = 0")
    ActivityStudent selectOneByActivityIdAndStuNum(Long actId, String studentNum);
}
