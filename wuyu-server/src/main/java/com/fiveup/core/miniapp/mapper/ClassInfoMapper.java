package com.fiveup.core.miniapp.mapper;

import com.fiveup.core.miniapp.model.ClassMini;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ClassInfoMapper {

    @Select("select * from basic_class where id=#{classId}")
    ClassMini getClassInfoByClassId(Long classId);

    @Select("select id from basic_class where grade=#{grade} and class_name=#{className}")
    Long getIdByGradeAndClass(String grade, String className);

    @Results(id = "ClassMini", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "grade", column = "grade"),
            @Result(property = "className", column = "class_name"),
            @Result(property = "classNum", column = "class"),
            @Result(property = "monitorId", column = "monitor_id"),
            @Result(property = "schoolId",column = "school_id")
    })
    @Select("select * from basic_class where id=(select class_id from basic_student WHERE student_num=#{studentNum})")
    ClassMini getStudentClass(Long studentNum);

    @Select("select class_id from basic_student where student_num=#{studentNum}")
    int getClassIdByStudentNum(int studentNum);
}
