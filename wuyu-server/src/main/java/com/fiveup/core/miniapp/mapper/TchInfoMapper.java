package com.fiveup.core.miniapp.mapper;

import com.fiveup.core.miniapp.model.TeacherMini;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TchInfoMapper {

    @Select("select * from basic_teacher where id=#{teacherId}")
    TeacherMini getTeacherById(Long teacherId);

    @Select("select * from basic_teacher where teacher_name=#{teacherName}")
    TeacherMini getTeacherByName(String teacherName);

    @Results(id = "TeacherMini", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "teacherName", column = "teacher_name"),
            @Result(property = "gender", column = "gender"),
            @Result(property = "phoneNum", column = "phone_num"),
            @Result(property = "position", column = "position"),
            @Result(property = "title",column = "title"),
            @Result(property = "role", column = "role"),
            @Result(property = "schoolId", column = "school_id"),
    })
    @Select("select * from basic_teacher where id=any(select teacher_id from basic_lesson where grade=#{gradeNum} and class_num=#{classNum})")
    List<TeacherMini> getTeacherInfoByCLass(int gradeNum,int classNum);
}
