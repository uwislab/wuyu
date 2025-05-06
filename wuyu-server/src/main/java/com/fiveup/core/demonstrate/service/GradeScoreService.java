package com.fiveup.core.demonstrate.service;

import com.fiveup.core.demonstrate.entity.GradeScore;
import com.fiveup.core.demonstrate.entity.qianDuan;
import com.fiveup.core.demonstrate.mapper.GradeScoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GradeScoreService {

    @Autowired
    private GradeScoreMapper gradeScoreMapper;


    public List<qianDuan> getGradeScore(String shuju) {
        List<GradeScore> list = gradeScoreMapper.getGradeScore(shuju);
        List<qianDuan> list1 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            GradeScore gradeScore = list.get(i);
            qianDuan qianDuan = new qianDuan();
            String name = Integer.toString(gradeScore.getData());
            qianDuan.setName(name);
            int[] data = new int[5];
            data[0] = gradeScore.getMoralityScore();
            data[1] = gradeScore.getIntelligenceScore();
            data[2] = gradeScore.getPhysicalScore();
            data[3] = gradeScore.getAestheticScore();
            data[4] = gradeScore.getLabourScore();
            qianDuan.setData(data);
            qianDuan.setType("line");
            qianDuan.setSmooth(true);
            list1.add(qianDuan);
        }
        return list1;
    }
}
