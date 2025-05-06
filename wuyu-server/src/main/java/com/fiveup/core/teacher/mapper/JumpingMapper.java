package com.fiveup.core.teacher.mapper;

import com.fiveup.core.management.pojo.PageDto;
import com.fiveup.core.management.pojo.PageDto1;
import com.fiveup.core.teacher.entity.teacher;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JumpingMapper {
    List<teacher> getTeacherByPage(long schoolId, PageDto1 dto, List<Long> classList);

    List<Integer> getTotalTeacherCount();
}
