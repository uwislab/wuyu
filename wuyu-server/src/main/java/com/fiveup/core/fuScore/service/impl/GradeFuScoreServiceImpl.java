package com.fiveup.core.fuScore.service.impl;

import com.fiveup.core.fuScore.mapper.GradeFuScoreMapper;
import com.fiveup.core.fuScore.model.ClassFuItemScore;
import com.fiveup.core.fuScore.model.ClassScore;
import com.fiveup.core.fuScore.model.GradeFuItemScore;
import com.fiveup.core.fuScore.model.GradeFuScore;
import com.fiveup.core.fuScore.service.GradeFuScoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author shilin
 * @date 2022/9/19
 */
@Slf4j
@Service
public class GradeFuScoreServiceImpl implements GradeFuScoreService {

    private List<GradeFuScore> gradeScoreList;

    @Resource
    private GradeFuScoreMapper gradeFuScoreMapper;

    @Override
    public List<GradeFuScore> getAll() {
        gradeScoreList = gradeFuScoreMapper.getAll();
        return gradeScoreList;
    }

    @Override
    public List<GradeFuScore> getGradeFuScoreByDate(int evaluateDate) {
        gradeScoreList = gradeFuScoreMapper.getGradeFuScoreByDate(evaluateDate);
        return gradeScoreList;
    }

    @Override
    public List<GradeFuScore> getGradeScore(int gradeId) {
        gradeScoreList = gradeFuScoreMapper.getGradeScore(gradeId);
        return gradeScoreList;
    }

    @Override
    public List<GradeFuItemScore> getGradeMoScoreList() {
        List<GradeFuItemScore> gradeFuItemScore;
        gradeFuItemScore = gradeFuScoreMapper.getGradeMoScoreList();
        return gradeFuItemScore;
    }

    @Override
    public List<GradeFuItemScore> getGradeMoScoreByDate(int date) {
        List<GradeFuItemScore> gradeFuItemScore;
        gradeFuItemScore = gradeFuScoreMapper.getGradeMoScoreByDate(date);
        return gradeFuItemScore;
    }

    @Override
    public List<ClassFuItemScore> getGradeMoScoreList(int gradeID) {
        List<ClassFuItemScore> classFuItemScore;
        classFuItemScore = gradeFuScoreMapper.getGradeMoScoreByGradeID(gradeID);
        return classFuItemScore;
    }

    @Override
    public List<GradeFuItemScore> getGradeInScoreList() {
        List<GradeFuItemScore> gradeFuItemScore;
        gradeFuItemScore = gradeFuScoreMapper.getGradeInScoreList();
        return gradeFuItemScore;
    }

    @Override
    public List<GradeFuItemScore> getGradeInScoreByDate(int date) {
        List<GradeFuItemScore> gradeFuItemScore;
        gradeFuItemScore = gradeFuScoreMapper.getGradeInScoreByDate(date);
        return gradeFuItemScore;
    }

    @Override
    public List<ClassFuItemScore> getGradeInScoreList(int gradeID) {
        System.out.println("...");
        List<ClassFuItemScore> classFuItemScore;
        classFuItemScore = gradeFuScoreMapper.getGradeInScoreByGradeID(gradeID);
        return classFuItemScore;
    }

    @Override
    public List<GradeFuItemScore> getGradePhScoreList() {
        List<GradeFuItemScore> gradeFuItemScore;
        gradeFuItemScore = gradeFuScoreMapper.getGradePhScoreList();
        return gradeFuItemScore;
    }

    @Override
    public List<GradeFuItemScore> getGradePhScoreByDate(int date) {
        List<GradeFuItemScore> gradeFuItemScore;
        gradeFuItemScore = gradeFuScoreMapper.getGradePhScoreByDate(date);
        return gradeFuItemScore;
    }

    @Override
    public List<ClassFuItemScore> getGradePhScoreList(int gradeID) {
        List<ClassFuItemScore> classFuItemScore;
        classFuItemScore = gradeFuScoreMapper.getGradePhScoreByGradeID(gradeID);
        return classFuItemScore;
    }

    @Override
    public List<GradeFuItemScore> getGradeAeScoreList() {
        List<GradeFuItemScore> gradeFuItemScore;
        gradeFuItemScore = gradeFuScoreMapper.getGradeAeScoreList();
        return gradeFuItemScore;
    }

    @Override
    public List<GradeFuItemScore> getGradeAeScoreByDate(int date) {
        List<GradeFuItemScore> gradeFuItemScore;
        gradeFuItemScore = gradeFuScoreMapper.getGradeAeScoreByDate(date);
        return gradeFuItemScore;
    }

    @Override
    public List<ClassFuItemScore> getGradeAeScoreList(int gradeID) {
        List<ClassFuItemScore> classFuItemScore;
        classFuItemScore = gradeFuScoreMapper.getGradeAeScoreByGradeID(gradeID);
        return classFuItemScore;
    }

    @Override
    public List<GradeFuItemScore> getGradeLaScoreList() {
        List<GradeFuItemScore> gradeFuItemScore;
        gradeFuItemScore = gradeFuScoreMapper.getGradeLaScoreList();
        return gradeFuItemScore;
    }

    @Override
    public List<GradeFuItemScore> getGradeLaScoreByDate(int date) {
        List<GradeFuItemScore> gradeFuItemScore;
        gradeFuItemScore = gradeFuScoreMapper.getGradeLaScoreByDate(date);
        return gradeFuItemScore;
    }

    @Override
    public List<ClassFuItemScore> getGradeLaScoreList(int gradeID) {
        List<ClassFuItemScore> classFuItemScore;
        classFuItemScore = gradeFuScoreMapper.getGradeLaScoreByGradeID(gradeID);
        return classFuItemScore;
    }

    @Override
    public List<Integer> getDateList() {
        List<Integer> dateList;
        dateList = gradeFuScoreMapper.getDateList();
        return dateList;
    }
}
