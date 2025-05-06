package com.fiveup.core.performanceevaluation.utils;

import com.fiveup.core.performanceevaluation.bean.StudentPerformance;
import com.fiveup.core.performanceevaluation.vo.SubjectScoreWeightVO;

import java.math.BigDecimal;

public class StudentPerformanceUtil {

    public static BigDecimal computeTotalScore(StudentPerformance studentPerformance,
                                               SubjectScoreWeightVO subjectScoreWeightVO) {
        BigDecimal bigDecimal = ((subjectScoreWeightVO.getVirtueWeight().multiply(BigDecimal.valueOf(studentPerformance.getVirtue())))
                .add(subjectScoreWeightVO.getIntelligenceWeight().multiply(BigDecimal.valueOf(studentPerformance.getIntelligence())))
                .add(subjectScoreWeightVO.getSportsWeight().multiply(BigDecimal.valueOf(studentPerformance.getSports())))
                .add(subjectScoreWeightVO.getArtWeight().multiply(BigDecimal.valueOf(studentPerformance.getArt())))
                .add(subjectScoreWeightVO.getLaborWeight().multiply(BigDecimal.valueOf(studentPerformance.getLabor()))));

        return bigDecimal;
    }

    public static String compareAvg(double avg,double stu,String subType) {
        if(avg-stu>0){
            return "你的"+subType+"成绩高于平均分"+String.format("%.2f", (avg-stu));
        }
        else if (avg-stu<0){
            return "你的"+subType+"成绩低于平均分"+String.format("%.2f", (stu-avg));
        }else {
            return "你的"+subType+"成绩刚好达到了平均分";
        }
    }
}
