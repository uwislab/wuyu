package com.fiveup.core.demonstrate.service;

import com.fiveup.core.demonstrate.entity.vo.ScoreAndClass13;
import com.fiveup.core.demonstrate.entity.vo.chartsObj;
import com.fiveup.core.management.common.CommonResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface StudentScoreService {
    public List<ScoreAndClass13> getScore(int page_num,
                                          int page_size,
                                          String student_num,
                                          String student_name,
                                          String class_name,
                                          String grade);

    List<chartsObj> getAllScore(String className,String gradeName);
    int getTableSize(String student_num,
                     String student_name,
                     String class_name,
                     String grade);

    List<String> getClassName();

    List<String> getGradeName();


    List<ScoreAndClass13> getOneScore(String id);
}
