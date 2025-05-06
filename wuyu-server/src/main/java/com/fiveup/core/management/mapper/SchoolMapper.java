package com.fiveup.core.management.mapper;

import com.fiveup.core.management.model.School;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 尔宣赫
 * @date 2022/4/25
 */
@Mapper
public interface SchoolMapper {
    School getSchoolById(Long schoolId);

    @Select("select id from basic_school")
    List<Long> getAllSchoolIds();

    @Select("select * from basic_school")
    List<School> getAllSchools();
}
