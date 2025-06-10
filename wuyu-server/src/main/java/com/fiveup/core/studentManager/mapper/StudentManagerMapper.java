package com.fiveup.core.studentManager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fiveup.core.studentManager.entity.StudentManager;
import com.fiveup.core.studentManager.pojo.PageBean;
import com.fiveup.core.studentManager.pojo.StudentManagerQuery;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentManagerMapper extends BaseMapper<StudentManager> {

    //查询学生
    PageBean<StudentManager> getStudent(StudentManagerQuery studentManagerQuery);
}
