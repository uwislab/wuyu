package com.fiveup.core.diagnose.service;


import com.fiveup.core.diagnose.bean.student_QTypeProportion;
import com.fiveup.core.diagnose.bean.student_QTypeScore;
import com.fiveup.core.diagnose.bean.student_classgrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QloseScoreAnalyseService {

    @Autowired
    QTypeProportionService qTypeProportionService;
    @Autowired
    QTypeScoreService qTypeScoreService;
    @Autowired
    studentscoreService stService;
    public String loseScoreAnalyse(String subject,String name,Long id){
        student_QTypeScore qs = new student_QTypeScore();
        qs=qTypeScoreService.slectQTypeS(id,name,subject);
        String analyse="";
        student_classgrade scg = new student_classgrade();
        /*获取学生点的对应年级*/
        scg=stService.getClass(name,id);
        student_QTypeProportion qp = new student_QTypeProportion();
        qp=qTypeProportionService.selectQTypeP(subject,scg.getStudentGrade());
        /*计算所丢分数*/
        int[] loseScore = new int[3];
        loseScore[0] = qp.getQtEasy()-qs.getEasyScore();
        loseScore[1] =qp.getQtMedium()-qs.getMediumScore();
        loseScore[2] =qp.getQtDifficuit()-qs.getDifficuitScore();
        if(loseScore[0]==0&&loseScore[1]==0&&loseScore[2]==0){
            analyse=analyse+"没有丢分的地方，请继续保持!";
            return analyse;
        }
        int lose=0;
        int loseflog=-1;
        for(int i=0;i<loseScore.length;i++){
            if(loseScore[i]>lose){
                lose=loseScore[i];
                loseflog=i;
            }
        }
        if(loseflog==0){
            analyse=analyse+"简单题丢分最多！简单题主要考察知识的基本概念，需要好好理解书本知识，须知万丈高楼平地起，注重基础" +
                    "才能稳步提高。";
        }
        else if(loseflog==1){
            analyse=analyse+"中等题丢分最多！中等题主要考察知识的运用能力，以及一些容易混淆的知识内容，这部分很重要，" +
                    "是能力上升的基础。";
        }
        else{
            analyse=analyse+"难题丢分最多！难题主要考察思维的扩展能力，观察力，将难点与所学知识相联系，解题的能力，不要灰心，" +
                    "一定能做好的！";
        }
        return analyse;
    }
}
