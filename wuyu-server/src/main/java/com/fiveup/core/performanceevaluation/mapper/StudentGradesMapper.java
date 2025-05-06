package com.fiveup.core.performanceevaluation.mapper;

import com.fiveup.core.performanceevaluation.bean.StudentGrades;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface StudentGradesMapper {

    /**
     * 获取所有学生和每个学生对应的班级
     * @return List
     */
    List<StudentGrades> getAllStudentClass();

    /**
     * 获取某个学生某个课程的分数(0:德育1:智育2:体育3:美育4:劳育)
     * @param classType
     * @param studentNum
     * @return int
     */
    Integer getScoreByClassTypeAndStudentNum(@Param("classType") int classType, @Param("studentNum") String studentNum);

    List<StudentGrades> getStudentClass(@Param("studentNum") String studentNum,
                                        @Param("studentName") String studentName,
                                        @Param("className") String className,
                                        @Param("grade") String grade);

    List<StudentGrades> getPersonal(@Param("studentNum") String studentNum, @Param("studentName") String studentName);
    List<String> getAllClass();

    List<String> getAllGrades();

    Integer getExcellent();

    Integer getGood();

    Integer getOrdinary();

    Integer getPass();

    Integer getNotPass();


}
