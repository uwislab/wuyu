package com.fiveup.core.overallOperation.mapper;

import com.fiveup.core.overallOperation.domain.*;
import jnr.ffi.annotations.In;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OverAllOperationMapper {
    //查询班级分数信息
    @Select("select * from fu_class_score where data = #{date} and grade_id = #{grade}")
    List<FuClassScore> getClassInfo(Integer grade,String date);

    //查询班级下的所有学生的分数信息录入和评阅情况
    // 1
    @Select("select * from fu_student_score where substring(class_id,1,1) = #{grade} and evaluate_date = #{date}")
    List<FuStudentScore> getStudentInfo(Integer grade, String date);


    //查找学生总数
    @Select("select count(*) from basic_student")
    Integer getTotalStudentNumber();
    //查找教师总数
    @Select("select count(*) from basic_teacher")
    Integer getTotalTeacherNumber();
    //查找班级总数
    @Select("select count(*) from basic_class")
    Integer getTotalClassNumber();
    //查找五育各个班级分数信息和录入以及评阅情况
    @Select("select * from fu_class_score where data = #{data} ")
    List<FuClassScore> getAllScores(String data);
    //查询班级，年级，学期下的所有学生的各科分数以及录入和评阅情况
    @Select("SELECT fs.student_name, fs.morality_score, fs.intelligence_score, fs.physical_score, fs.aesthetic_score, fs.labour_score, fs.isreview, fs.isenter FROM fu_student_score fs WHERE fs.class_id = #{stuGrade}*10+#{stuClass} AND fs.evaluate_date = #{stuTerm}")
    List<StudentScore> getStudentScore(String stuGrade, String stuClass, String stuTerm);

    //查找当前班级学生总数
    @Select("select count(*) FROM fu_student_score fs WHERE fs.class_id = #{stuGrade}*10+#{stuClass} AND fs.evaluate_date = #{stuTerm}")
    Integer getTotalClassStudentNumber(String stuGrade, String stuClass, String stuTerm);

    //查找当前班级学生成绩已录入总数
    @Select("select count(*) FROM fu_student_score fs WHERE fs.class_id = #{stuGrade}*10+#{stuClass} AND fs.evaluate_date = #{stuTerm} AND fs.isenter = 0")
    Integer getClassStudentEnterNumber(String stuGrade, String stuClass, String stuTerm);

    //修改录入
    @Update("update fu_student_score set morality_score=#{studentScore.moralityScore}, intelligence_score=#{studentScore.intelligenceScore}, physical_score=#{studentScore.physicalScore} ,aesthetic_score=#{studentScore.aestheticScore} ,labour_score=#{studentScore.labourScore} , isenter = 0,isreview=0 where student_name=#{studentScore.studentName} and evaluate_date=#{term}")
    Integer updateStu(FuStudentDto fuStudentDto);
}
