package com.fiveup.core.fuScore.service.impl;

import com.fiveup.core.fuScore.mapper.ClassFuScoreMapper;
import com.fiveup.core.fuScore.mapper.StudentFuScoreMapper;
import com.fiveup.core.fuScore.model.ClassFuScore;
import com.fiveup.core.fuScore.model.ClassScore;
import com.fiveup.core.fuScore.model.FuClassScore;
import com.fiveup.core.fuScore.model.FuScore;
import com.fiveup.core.fuScore.model.StudentFuScore;
import com.fiveup.core.fuScore.service.ClassFuScoreService;
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
public class ClassFuScoreServiceImpl implements ClassFuScoreService {


    @Resource
    private ClassFuScoreMapper classFuScoreMapper;

    @Resource
    private StudentFuScoreMapper studentFuScoreMapper;

    @Override
    public List<ClassScore> getClassScore(int classId, int gradeId) {
        List<ClassScore> classScoreList;
        classScoreList = classFuScoreMapper.getClassScore(gradeId, classId);
        return classScoreList;
    }

    @Override
    public List<ClassScore> getClassScoreByGradeAndClassAndItem(int gradeId, String className, int itemId) {
        List<ClassScore> classScoreList;

        classScoreList = classFuScoreMapper.getClassScoreByGradeAndClass(gradeId, className, itemId);
        return classScoreList;
    }

    @Override
    public List<ClassScore> getClassScoreByGradeIdAndClassId(int classId, int gradeId) {
        List<ClassScore> classScoreList;

        classScoreList = classFuScoreMapper.getClassScoreByClassIdAndGradeId(classId, gradeId);
        return classScoreList;
    }

    @Override
    public List<StudentFuScore> getStudentsFuScoreList(int gradeId, int classId, int date) {
        List<StudentFuScore> studentFuScore;
        studentFuScore = classFuScoreMapper.getStudentsFuScoreList(gradeId, classId, date);
        return studentFuScore;
    }

    @Override
    public StudentFuScore getLastScore(String studentNum, int date) {
        StudentFuScore studentFuScore;
        studentFuScore = classFuScoreMapper.getLastScore(studentNum, date);
        return studentFuScore;
    }

    @Override
    public List<ClassFuScore> getStudentsMoScoreList(Long classId) {
        List<ClassFuScore> classScoreList;
        classScoreList = classFuScoreMapper.getStudentsMoScoreList(classId);
        return classScoreList;
    }

    @Override
    public List<ClassFuScore> getStudentsInScoreList(Long classId) {
        List<ClassFuScore> classScoreList;
        classScoreList = classFuScoreMapper.getStudentsInScoreList(classId);
        return classScoreList;
    }

    @Override
    public List<ClassFuScore> getStudentsPhScoreList(Long classId) {
        List<ClassFuScore> classScoreList;
        classScoreList = classFuScoreMapper.getStudentsPhScoreList(classId);
        return classScoreList;
    }

    @Override
    public List<ClassFuScore> getStudentsAeScoreList(Long classId) {
        List<ClassFuScore> classScoreList;
        classScoreList = classFuScoreMapper.getStudentsAeScoreList(classId);
        return classScoreList;
    }

    @Override
    public List<ClassFuScore> getStudentsLaScoreList(Long classId) {
        List<ClassFuScore> classScoreList;
        classScoreList = classFuScoreMapper.getStudentsLaScoreList(classId);
        return classScoreList;
    }

    @Override
    public List<Integer> getClassDataList() {
        List<Integer> dataList;
        dataList = classFuScoreMapper.getClassDataList();
        return dataList;
    }

    @Override
    public List<StudentFuScore> getClassScoreByGradeAndClass(int classId) {
        return studentFuScoreMapper.getStudentFuScoreByClassId(classId);
    }

    @Override
    public int insertFuScore(FuScore fuScore){
        return classFuScoreMapper.insertFuScore(fuScore);
    }

    @Override
    public List<ClassScore> getWorkList(int classId, int gradeId) {
        return classFuScoreMapper.getWorkList(classId, gradeId);
    }

    @Override
    public int addFuClassScore(FuClassScore fuClassScore) {
        return classFuScoreMapper.addFuClassScore(fuClassScore);
    }

}
