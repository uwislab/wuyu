package com.fiveup.core.studentManager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fiveup.core.management.model.School;
import com.fiveup.core.questionnaire.entity.Class;
import com.fiveup.core.studentManager.entity.StudentManager;
import org.apache.ibatis.annotations.Select;
import com.fiveup.core.studentManager.pojo.StudentManagerQuery;
import com.fiveup.core.studentManager.pojo.StudentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

import java.util.Set;

public interface StudentManagerMapper extends BaseMapper<StudentManager> {
    @Select("select distinct (student_num) from basic_student where deleted=0")
    Set<String> selectnum();

    @Select("select concat(grade,'#',class) from basic_class where deleted=0 group by class,grade;")
    Set<String> selectclassgrade();

    //分页查询学生
    List<StudentVO> getStudentPage(StudentManagerQuery studentManagerQuery);

    //获取学校列表
    @Select("select * from basic_school")
    List<School> getSchool();

    //获取班级列表
    @Select("select DISTINCT class_name from basic_class order by class_name")
    List<String> getClassName();

    //获取年纪列表
    @Select("select DISTINCT grade_id from basic_student order by grade_id")
    List<Integer> getGrade();
}
