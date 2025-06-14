package com.fiveup.core.studentManager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fiveup.core.studentManager.entity.StudentManager;
import org.apache.ibatis.annotations.Select;
import com.fiveup.core.studentManager.pojo.StudentManagerQuery;
import com.fiveup.core.studentManager.pojo.StudentVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

import java.util.Set;

public interface StudentManagerMapper extends BaseMapper<StudentManager> {
    @Select("select distinct (student_num) from basic_student")
    Set<String> selectnum();

    @Select("select concat(grade,'#',class) from basic_class group by class,grade;")
    Set<String> selectclassgrade();

    //分页查询学生
    List<StudentVO> getStudentPage(StudentManagerQuery studentManagerQuery);
}
