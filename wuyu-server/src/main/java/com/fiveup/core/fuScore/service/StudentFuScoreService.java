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

    StudentSemesterScore getStudentSemesterScores(int studentId, String studentName, String semester);

    List<StuSemesterTotalScore> getStuSemester(int studentId, String studentName);
}
