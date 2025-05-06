package com.fiveup.core.remark.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fiveup.core.remark.entity.studentScore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MyscoreMapper extends BaseMapper<studentScore> {
    @Select("SELECT * FROM fu_student_score WHERE student_num = #{studentNum}")
    List<studentScore> selSome(String studentNum);

    @Select("SELECT * FROM fu_student_score WHERE student_num = #{studentNum} AND evaluate_date=#{evaluateDate}")
    studentScore selectOneScore(String studentNum,int evaluateDate);


    @Select("SELECT * from re_student a,basic_student b where a.name=b.student_name and b.student_num=#{studentNum} and evaluate_date=#{evaluateDate}")
    studentScore selectOneScore2(String studentNum,int evaluateDate);
}
