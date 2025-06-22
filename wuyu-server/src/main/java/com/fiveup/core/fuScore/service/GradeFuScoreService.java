package com.fiveup.core.fuScore.service;

import com.fiveup.core.fuScore.model.*;

import java.util.List;
import java.util.Map;

/**
 * @author shilin
 * @date 2022/9/19
 */
public interface GradeFuScoreService {

    List<GradeFuScore> getAll();

    List<GradeFuScore> getGradeFuScoreByDate(int evaluateDate);

    List<GradeFuScore> getGradeScore(int gradeId);

    List<GradeFuItemScore> getGradeMoScoreList();

    List<GradeFuItemScore> getGradeMoScoreByDate(int date);

    List<ClassFuItemScore> getGradeMoScoreList(int gradeID);

    List<GradeFuItemScore> getGradeInScoreList();

    List<GradeFuItemScore> getGradeInScoreByDate(int date);

    List<ClassFuItemScore> getGradeInScoreList(int gradeID);

    List<GradeFuItemScore> getGradePhScoreList();

    List<GradeFuItemScore> getGradePhScoreByDate(int date);

    List<ClassFuItemScore> getGradePhScoreList(int gradeID);

    List<GradeFuItemScore> getGradeAeScoreList();

    List<GradeFuItemScore> getGradeAeScoreByDate(int date);

    List<ClassFuItemScore> getGradeAeScoreList(int gradeID);

    List<GradeFuItemScore> getGradeLaScoreList();

    List<GradeFuItemScore> getGradeLaScoreByDate(int date);

    List<ClassFuItemScore> getGradeLaScoreList(int gradeID);

    List<Integer> getDateList();
}
