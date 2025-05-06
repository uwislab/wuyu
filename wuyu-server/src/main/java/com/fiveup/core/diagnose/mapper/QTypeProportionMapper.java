package com.fiveup.core.diagnose.mapper;


import com.fiveup.core.diagnose.bean.student_QTypeProportion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface QTypeProportionMapper {

    @Select("select qt_easy,qt_medium,qt_difficuit from di_qtypeproportion where qt_subject=#{subject} and qt_grade=#{grade}")
    public student_QTypeProportion selectQTypeP(String subject,int grade);
}
