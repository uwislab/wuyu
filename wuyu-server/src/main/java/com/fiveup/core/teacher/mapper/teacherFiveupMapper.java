package com.fiveup.core.teacher.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fiveup.core.teacher.entity.teacher;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface teacherFiveupMapper extends BaseMapper<teacher> {
     List<teacher> selectAllUser();


}
