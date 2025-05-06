package com.fiveup.core.miniapp.mapper;

import com.fiveup.core.miniapp.model.ActivityFamily;
import com.fiveup.core.miniapp.model.ActivityStudent;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ActivityScoreMapper {

    @Results(id = "ActivityFamily", value = {
            @Result(property = "activityName", column = "name"),
            @Result(property = "activityContent", column = "content"),
            @Result(property = "activityPlace", column = "place"),
            @Result(property = "activityId", column = "id"),
            @Result(property = "teacherId", column = "teacher_id"),
            @Result(property = "startTimeDate",column = "start_time"),
            @Result(property = "withParent", column = "withParent"),
            @Result(property = "activityFee", column = "activityFee"),
            @Result(property = "shouldCarryStuff", column = "shouldCarryStuff"),
            @Result(property = "wearingLimit",column = "wearingLimit")
    })

    @Select("select * from fu_activity where class_id= #{classId} order by start_time DESC")
    List<ActivityFamily> getActivitiesByClassId(String classId);


    @Results(id = "ActivityStudent", value = {
            @Result(property = "activityId", column = "activity_id"),
            @Result(property = "studentNum", column = "student_num"),
            @Result(property = "stuActScore", column = "stu_act_score"),
            @Result(property = "activityScoreFamily", column = "activity_score_family"),
            @Result(property = "activityScoreTotal",column = "activity_score_total")
    })


    @Select("select * from activity_student " +
            "where student_num= #{studentNum} and activity_id= #{activityId}")
    ActivityStudent getActivitiesByStudentNumAndActivityId(ActivityStudent activityStudent);

    @Insert("insert into activity_student(activity_id,student_num,stu_act_score,activity_score_family,activity_score_total) " +
            "values(#{activityId},#{studentNum},#{stuActScore},#{activityScoreFamily},#{activityScoreTotal})")
    int familyAddActivityScore(ActivityStudent activityStudent);

    @Update("update activity_student " +
            "set " +
            "activity_score_family=#{activityScoreFamily}," +
            "activity_score_total=#{activityScoreTotal} " +
            "where student_num= #{studentNum} and activity_id= #{activityId}")
    int familyUpdateActivityScore(ActivityStudent activityStudent);

}

