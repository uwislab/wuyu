package com.fiveup.core.studentManager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fiveup.core.studentManager.entity.StudentManager;
import com.fiveup.core.studentManager.pojo.StudentManagerQuery;
import com.fiveup.core.studentManager.pojo.StudentVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentManagerMapper extends BaseMapper<StudentManager> {

    //分页查询学生
    List<StudentVO> getStudentPage(StudentManagerQuery studentManagerQuery);
}
