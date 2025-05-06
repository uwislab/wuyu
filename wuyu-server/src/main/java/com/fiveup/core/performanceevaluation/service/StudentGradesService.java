package com.fiveup.core.performanceevaluation.service;

import com.fiveup.core.performanceevaluation.bean.StudentGrades;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public interface StudentGradesService {
    PageInfo<StudentGrades> getStudentGradesByPage(int pageSize, int pageNum);

    PageInfo<StudentGrades> getStudentGrades(int pageNum, int pageSize, String studentNum, String studentName,
                                             String className, String grade);

    List<String> getAllClass();
    List<String> getAllGrades();

    Map<String,Integer> getChartData(String studentNum, String studentName, String className, String grade);
}
