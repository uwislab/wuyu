package com.fiveup.core.cultivation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fiveup.core.cultivation.entity.BasicTeacher;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author Harvi
 */
@Component
@Mapper
public interface BasicTeacherMapper extends BaseMapper<BasicTeacher> { }
