package com.fiveup.core.fuScore.service;

import com.fiveup.core.fuScore.model.ClassFuScore;
import com.fiveup.core.fuScore.model.ClassScore;
import com.fiveup.core.fuScore.model.FuClassScore;
import com.fiveup.core.fuScore.model.FuScore;
import com.fiveup.core.fuScore.model.StudentFuScore;

import java.util.List;

/**
 * @author shilin
 * @date 2022/9/19
 */
public interface ClassFuScoreService {

    List<ClassScore> getClassScore(int classId, int gradeId);

    List<ClassScore> getClassScoreByGradeAndClassAndItem(int gradeId, String className, int itemId);

    List<ClassScore> getClassScoreByGradeIdAndClassId(int classId, int gradeId);

    List<StudentFuScore> getStudentsFuScoreList(int gradeId, int classId, int date);

    StudentFuScore getLastScore(String studentNum, int date);

    List<ClassFuScore> getStudentsMoScoreList(Long classId);

    List<ClassFuScore> getStudentsInScoreList(Long classId);

    List<ClassFuScore> getStudentsPhScoreList(Long classId);

    List<ClassFuScore> getStudentsAeScoreList(Long classId);

    List<ClassFuScore> getStudentsLaScoreList(Long classId);

    List<Integer> getClassDataList();

    List<StudentFuScore> getClassScoreByGradeAndClass(int classId);

    int insertFuScore(FuScore fuScore);

    List<ClassScore> getWorkList(int classId, int gradeId);

    int addFuClassScore(FuClassScore fuClassScore);

}
