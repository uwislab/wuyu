package com.fiveup.core.demonstrate.mapper;

import com.fiveup.core.demonstrate.entity.GradeScore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface GradeScoreMapper {

    @Select("select * from fu_grade_score where grade_name = #{shuju}")
    List<GradeScore> getGradeScore(String shuju);




}
