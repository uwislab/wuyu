package com.fiveup.core.fuScore.service;

import com.fiveup.core.fuScore.model.*;

import java.util.List;
import java.util.Map;

/**
 * @author shilin
 * @date 2022/9/19
 */
public interface StudentFuScoreService {
    /**
     * 获取班级五育平均成绩
     */
    Map<String, Object> getClassAverageScores(Integer classId);
    List<StudentFuScore> getStudentsFuScore(int studentId);


    List<StudentFuScore> getStudentsFuScore(String studentName, int studentId);

    List<StudentFuScore> getScoreByInfo(String info);

    StudentSemesterScore getStudentSemesterScores(Integer studentId, String studentName, String semester);

    List<StuSemesterTotalScore> getStuSemester(Integer studentId, String studentName);

    List<StudentInfo> searchStudents(String keyword);

    List<StudentSemesterDto> getStudentSemesters(Integer studentId);

    ClassAndGradeScoreResponse getClassAndGradeAvgScores(Integer studentId, String semesterName , int gradeLevel);
}
