package com.fiveup.core.remark.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fiveup.core.remark.entity.studentScore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StudentScoreALLMapper extends BaseMapper<studentScore> {
    @Select("SELECT * FROM fu_student_score WHERE student_num = #{studentNum} AND evaluate_date=#{evaluateDate}")
    List<studentScore> selectByStudentNum(@Param("studentNum") String studentNum,@Param("evaluateDate") int evaluateDate);

    @Select("SELECT * FROM fu_student_score WHERE student_num = #{studentNum} AND evaluate_date=#{evaluateDate}")
    List<studentScore> selectByStudentNumAndDate( String studentNum, int evaluateDate);
}