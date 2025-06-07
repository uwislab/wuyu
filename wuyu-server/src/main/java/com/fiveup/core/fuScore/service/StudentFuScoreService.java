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

    StudentSemesterScore getStudentSemesterScores(int studentId, String studentName, String semester);

    List<StuSemesterTotalScore> getStuSemester(int studentId, String studentName);
}
