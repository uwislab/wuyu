package com.fiveup.core.fuScore.service;

import com.fiveup.core.fuScore.model.*;

import java.util.List;

/**
 * @author shilin
 * @date 2022/9/19
 */
public interface StudentFuScoreService {

    List<StudentFuScore> getStudentsFuScore(int studentId);


    List<StudentFuScore> getStudentsFuScore(String studentName, int studentId);

    List<StudentFuScore> getScoreByInfo(String info);

    StudentSemesterScore getStudentSemesterScores(Integer studentId, String studentName, String semester);

    List<StuSemesterTotalScore> getStuSemester(Integer studentId, String studentName);

    List<StudentInfo> searchStudents(String keyword);

    List<StudentSemesterDto> getStudentSemesters(Integer studentId);
}
