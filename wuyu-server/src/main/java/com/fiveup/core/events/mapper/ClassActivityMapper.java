package com.fiveup.core.events.mapper;

import com.fiveup.core.events.model.ClassActivity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ClassActivityMapper {

    @Select("select * from class_activity where activity_id = #{activityId}")
    List<ClassActivity> getClassActivityByActivityId(Long activityId);

    void deleteItemsByActivityId(Long activityId);

    void inertOne(Long activityId, Long classId);

}
