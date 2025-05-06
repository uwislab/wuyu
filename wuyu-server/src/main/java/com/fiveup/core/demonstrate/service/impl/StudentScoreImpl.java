package com.fiveup.core.demonstrate.service.impl;

import com.fiveup.core.demonstrate.entity.vo.ScoreAndClass13;
import com.fiveup.core.demonstrate.entity.vo.chartsObj;
import com.fiveup.core.demonstrate.mapper.StudentScoreMapper;
import com.fiveup.core.demonstrate.service.StudentScoreService;
import com.fiveup.core.management.common.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class StudentScoreImpl implements StudentScoreService {

    @Autowired
    StudentScoreMapper studentScoreMapper;

    @Override
    public List<ScoreAndClass13> getScore(int page_num,
                                          int page_size,
                                          String student_num,
                                          String student_name,
                                          String class_name,
                                          String grade) {
        List<ScoreAndClass13> score = studentScoreMapper.getScore(page_num, page_size,
                student_num, student_name,
                class_name, grade);

        return score;
    }

    @Override
    public List<chartsObj> getAllScore(String className,String gradeName) {
        List<ScoreAndClass13> allScore = studentScoreMapper.getAllScore(className,gradeName);
        ArrayList<chartsObj> chartsObjs = new ArrayList<>();
        chartsObj you = new chartsObj(0, "优");
        chartsObj liang = new chartsObj(0, "良");
        chartsObj zhong = new chartsObj(0, "中");
        chartsObj jige = new chartsObj(0, "及格");
        chartsObj bujige = new chartsObj(0, "不及格");

        allScore.forEach(item->{
            int all = item.getMoralityScore() + item.getAestheticScore() + item.getIntelligenceScore() + item.getLabourScore() + item.getPhysicalScore();
            if (all<60*5){
                bujige.setValue(bujige.getValue()+1);
            } else if (all < 70 * 5) {
                jige.setValue(jige.getValue()+1);
            } else if (all<80*5) {
                zhong.setValue(zhong.getValue()+1);
            }else if (all<90*5) {
                liang.setValue(liang.getValue()+1);
            }else if (all<100*5) {
                you.setValue(you.getValue()+1);
            }
        });
        chartsObjs.add(you);
        chartsObjs.add(liang);
        chartsObjs.add(zhong);
        chartsObjs.add(jige);
        chartsObjs.add(bujige);
        return chartsObjs;
    }

    @Override
    public int getTableSize(String student_num,
                            String student_name,
                            String class_name,
                            String grade) {
        return studentScoreMapper.getTableSize(student_num, student_name,
                class_name, grade);
    }

    @Override
    public List<String> getClassName() {
        return studentScoreMapper.getClassName();
    }

    @Override
    public List<String> getGradeName() {
        return studentScoreMapper.getGradeName();
    }

    @Override
    public List<ScoreAndClass13> getOneScore(String id) {
        List<ScoreAndClass13> oneScore = studentScoreMapper.getOneScore(id);
        return oneScore;
    }


}
