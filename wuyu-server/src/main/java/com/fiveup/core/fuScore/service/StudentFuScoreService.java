package com.fiveup.core.fuScore.service;

import com.fiveup.core.fuScore.model.ClassFuScore;
import com.fiveup.core.fuScore.model.ClassScore;
import com.fiveup.core.fuScore.model.StudentFuScore;

import java.util.List;

/**
 * @author shilin
 * @date 2022/9/19
 */
public interface StudentFuScoreService {

    List<StudentFuScore> getStudentsFuScore(int studentId);


    List<StudentFuScore> getStudentsFuScore(String studentName, int studentId);

    List<StudentFuScore> getScoreByInfo(String info);
}
