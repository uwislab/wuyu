package com.fiveup.core.studentManager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fiveup.core.studentManager.entity.StudentManager;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

public interface StudentManagerMapper extends BaseMapper<StudentManager> {
    @Select("select distinct (student_num) from basic_student")
    Set<String> selectnum();

    @Select("select concat(grade,'#',class) from basic_class group by class,grade;")
    Set<String> selectclassgrade();
}
