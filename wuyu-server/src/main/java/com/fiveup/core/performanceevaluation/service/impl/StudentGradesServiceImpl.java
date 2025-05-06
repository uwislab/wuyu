package com.fiveup.core.performanceevaluation.service.impl;

import com.fiveup.core.performanceevaluation.bean.StudentGrades;
import com.fiveup.core.performanceevaluation.mapper.StudentGradesMapper;
import com.fiveup.core.performanceevaluation.service.StudentGradesService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentGradesServiceImpl implements StudentGradesService {

    @Autowired
    private StudentGradesMapper studentGradesMapper;


    @Override
    public PageInfo<StudentGrades> getStudentGradesByPage(int pageNum, int pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        List<StudentGrades> list = studentGradesMapper.getAllStudentClass();
        for (int i = 0;i < list.size();i++) {
            String studentNum = list.get(i).getStudentNum();
            Integer score0 = studentGradesMapper.getScoreByClassTypeAndStudentNum(0,studentNum);
            if (score0 == null) {score0 = 0;}
            Integer score1 = studentGradesMapper.getScoreByClassTypeAndStudentNum(1,studentNum);
            if (score1 == null) {score1 = 0;}
            Integer score2 = studentGradesMapper.getScoreByClassTypeAndStudentNum(2,studentNum);
            if (score2 == null) {score2 = 0;}
            Integer score3 = studentGradesMapper.getScoreByClassTypeAndStudentNum(3,studentNum);
            if (score3 == null) {score3 = 0;}
            Integer score4 = studentGradesMapper.getScoreByClassTypeAndStudentNum(4,studentNum);
            if (score4 == null) {score4 = 0;}
            Integer sum = score0 + score1 + score2 + score3 + score4;
            list.get(i).setScore(sum);
        }
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<StudentGrades> getStudentGrades(int pageNum, int pageSize, String studentNum, String studentName,
                                             String className, String grade) {
        PageHelper.startPage(pageNum, pageSize);
        List<StudentGrades> list = studentGradesMapper.getStudentClass(studentNum,studentName, className,grade);
        for (StudentGrades studentGrades : list) {
            studentGrades.setScore(studentGrades.getMoralityScore() +
                    studentGrades.getIntelligenceScore() + studentGrades.getPhysicalScore() +
                    studentGrades.getAestheticScore() + studentGrades.getLabourScore());
        }
        return new PageInfo<>(list);
    }

    @Override
    public Map<String,Integer> getChartData(String studentNum, String studentName, String className, String grade) {
        Map<String,Integer> map = new HashMap<>();
        List<StudentGrades> list = studentGradesMapper.getStudentClass(studentNum,studentName, className,grade);
        int excellent = 0;
        int good = 0;
        int ordinary = 0;
        int pass = 0;
        int notPass = 0;
        for (StudentGrades studentGrades : list) {
            if(studentGrades.getMoralityScore() >= 60) {
                if(studentGrades.getMoralityScore() >= 70) {
                    if(studentGrades.getMoralityScore() >= 80) {
                        if(studentGrades.getMoralityScore() >= 90) {
                            excellent++;
                        }
                        else {good++;}
                    }
                    else {ordinary++;}
                }
                else {pass++;}
            }
            else {notPass++;}
            if(studentGrades.getIntelligenceScore() >= 60) {
                if(studentGrades.getIntelligenceScore() >= 70) {
                    if(studentGrades.getIntelligenceScore() >= 80) {
                        if(studentGrades.getIntelligenceScore() >= 90) {
                            excellent++;
                        }
                        else {good++;}
                    }
                    else {ordinary++;}
                }
                else {pass++;}
            }
            else {notPass++;}
            if(studentGrades.getPhysicalScore() >= 60) {
                if(studentGrades.getPhysicalScore() >= 70) {
                    if(studentGrades.getPhysicalScore() >= 80) {
                        if(studentGrades.getPhysicalScore() >= 90) {
                            excellent++;
                        }
                        else {good++;}
                    }
                    else {ordinary++;}
                }
                else {pass++;}
            }
            else {notPass++;}
            if(studentGrades.getAestheticScore() >= 60) {
                if(studentGrades.getAestheticScore() >= 70) {
                    if(studentGrades.getAestheticScore() >= 80) {
                        if(studentGrades.getAestheticScore() >= 90) {
                            excellent++;
                        }
                        else {good++;}
                    }
                    else {ordinary++;}
                }
                else {pass++;}
            }
            else {notPass++;}
            if(studentGrades.getLabourScore() >= 60) {
                if(studentGrades.getLabourScore() >= 70) {
                    if(studentGrades.getLabourScore() >= 80) {
                        if(studentGrades.getLabourScore() >= 90) {
                            excellent++;
                        }
                        else {good++;}
                    }
                    else {ordinary++;}
                }
                else {pass++;}
            }
            else {notPass++;}
        }
        map.put("优", excellent);
        map.put("良好", good);
        map.put("中", ordinary);
        map.put("及格", pass);
        map.put("不及格", notPass);
        return map;
    }
    @Override
    public List<String> getAllClass() {
        return studentGradesMapper.getAllClass();
    }

    @Override
    public List<String> getAllGrades() {
        return studentGradesMapper.getAllGrades();
    }
}
