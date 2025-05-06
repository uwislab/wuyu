package com.fiveup.core.overallOperation.service;

import com.fiveup.core.common.result.Result;
import com.fiveup.core.overallOperation.domain.*;

import java.util.List;

public interface OverallOperationService {
public List<List<List<FuClassScore>>> getTopThreeByYear(String data);

public List<Pie> pie();

public List<FuVo> getClassInfo(Integer grade,Integer term);

public List<StudentRatio> getStudentRation(Integer grade,Integer term);

public List<StudentScore> getStudentScore(String stuGrade, String stuClass, String stuTerm);

public StudentRatio getClassStudentEnter(String stuGrade, String stuClass, String stuTerm);

public Integer updateStu(FuStudentDto stu);
}
