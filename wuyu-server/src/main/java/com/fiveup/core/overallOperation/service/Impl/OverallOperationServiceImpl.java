package com.fiveup.core.overallOperation.service.Impl;

import com.fiveup.core.overallOperation.domain.*;
import com.fiveup.core.overallOperation.domain.StudentScore;
import com.fiveup.core.overallOperation.mapper.OverAllOperationMapper;
import com.fiveup.core.overallOperation.service.OverallOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OverallOperationServiceImpl implements OverallOperationService {

    @Autowired
    OverAllOperationMapper overAllOperaitons;


    @Override
    public List<FuVo> getClassInfo(Integer grade,Integer term) {
        String date = term==0 ? "202012" :"202107";
        List<FuClassScore> fuClassScores = overAllOperaitons.getClassInfo(grade,date);
        List<FuStudentScore> fuStudentScores = overAllOperaitons.getStudentInfo(grade,date);
        List<FuVo> list = new ArrayList<>();
        //封装班级的分数信息
        for (int  i= 0;  i< fuClassScores.size(); i++) {
            FuVo fuVo = new FuVo();
            fuVo.setClassName(fuClassScores.get(i).getClassName());
            fuVo.setMoralityScore(fuClassScores.get(i).getMoralityScore());
            fuVo.setIntelligenceScore(fuClassScores.get(i).getIntelligenceScore());
            fuVo.setPhysicalScore(fuClassScores.get(i).getPhysicalScore());
            fuVo.setAestheticScore(fuClassScores.get(i).getAestheticScore());
            fuVo.setLabourScore(fuClassScores.get(i).getLabourScore());
            list.add(fuVo);
        }
        //封装三个班级的录入和评阅信息
        for (int i = 0; i < fuStudentScores.size(); i++) {
            if((fuStudentScores.get(i).getClassId())%10-1>2){
                continue;
            }
            if(fuStudentScores.get(i).getIsReview()==1){
                list.get((fuStudentScores.get(i).getClassId())%10-1).setReviewed(false);
            }
            if(fuStudentScores.get(i).getIsEnter()==1){
                list.get((fuStudentScores.get(i).getClassId())%10-1).setGradeEntered(false);
            }
        }
        return list;
    }

    @Override
    public List<StudentRatio> getStudentRation(Integer grade,Integer term) {
        String date = term==0 ? "202012" :"202107";
        List<FuStudentScore> fuStudentScores = overAllOperaitons.getStudentInfo(grade,date);
        List<FuStudentScore> class1 = new ArrayList<>();
        List<FuStudentScore> class2 = new ArrayList<>();
        List<FuStudentScore> class3 = new ArrayList<>();
        //将三个班的学生信息封装到不同列表里
        List<List<FuStudentScore>> list = new ArrayList<>();
        for (int i = 0; i < fuStudentScores.size(); i++) {
            if((fuStudentScores.get(i).getClassId())%10-1>2){
                continue;
            }
            switch ((fuStudentScores.get(i).getClassId())%10){
                case 1:class1.add(fuStudentScores.get(i));
                       break;
                case 2:class2.add(fuStudentScores.get(i));
                    break;
                case 3:class3.add(fuStudentScores.get(i));
                    break;
            }
        }
        list.add(class1);
        list.add(class2);
        list.add(class3);
        //统计各个班的已录入和已评阅的人数
        List<StudentRatio> studentRatios = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            StudentRatio studentRatio = new StudentRatio();
            int isEnter=0;
            int isReview=0;
            for (int j = 0; j < list.get(i).size(); j++) {
                if (list.get(i).get(j).getIsReview()==0) {
                    isReview++;
                }
                if (list.get(i).get(j).getIsEnter()==0) {
                    isEnter++;
                }
            }
            studentRatio.setIsReview(isReview);
            studentRatio.setIsEntered(isEnter);
            studentRatio.setTotalNum(list.get(i).size());
            studentRatios.add(studentRatio);
        }
        //将数据返回
        return studentRatios;
    }

    @Override
    public List<List<List<FuClassScore>>> getTopThreeByYear(String data) {
        //记录总的五育各项分数前三
        List<List<List<FuClassScore>>> topThree = new ArrayList<>();
        //纪录五育各项分数前三
        List<List<FuClassScore>> topThreeMoralityScore = new ArrayList<>();
        List<List<FuClassScore>> topThreeIntelligenceScore = new ArrayList<>();
        List<List<FuClassScore>> topThreePhysicalScore = new ArrayList<>();
        List<List<FuClassScore>> topThreeAestheticScore = new ArrayList<>();
        List<List<FuClassScore>> topThreeLabourScore = new ArrayList<>();
        //从数据库查出所有数据
        List<FuClassScore> fuClassScores = new ArrayList<>();
        fuClassScores = overAllOperaitons.getAllScores(data);
        //给各个班年级赋值
        for (int i = 0; i < fuClassScores.size(); i++) {
            String grade="";
            switch(fuClassScores.get(i).getGradeId()){
                case 1: grade="一年级";
                        break;
                case 2: grade="二年级";
                    break;
                case 3: grade="三年级";
                    break;
                case 4: grade="四年级";
                    break;
                case 5: grade="五年级";
                    break;
                case 6: grade="六年级";
                    break;
            }
            fuClassScores.get(i).setClassName(grade+fuClassScores.get(i).getClassName());
        }
        //德育前三
        fuClassScores = fuClassScores.stream().sorted(Comparator.comparing(FuClassScore::getMoralityScore).reversed()).collect(Collectors.toList());
        for (int i = 0; i <fuClassScores.size(); i++) {
            List<FuClassScore> list = new ArrayList<>();
            list.add(fuClassScores.get(i));
            while(i+1<fuClassScores.size() && fuClassScores.get(i).getMoralityScore()==fuClassScores.get(i+1).getMoralityScore()){
                list.add(fuClassScores.get(i+1));
                i+=1;
            }
            topThreeMoralityScore.add(list);
            if(topThreeMoralityScore.size()==3){
                break;
            }
        }
        //智育前三
        fuClassScores = fuClassScores.stream().sorted(Comparator.comparing(FuClassScore::getIntelligenceScore).reversed()).collect(Collectors.toList());
        for (int i = 0; i <fuClassScores.size(); i++) {
            System.out.println(fuClassScores);
            List<FuClassScore> list = new ArrayList<>();
            list.add(fuClassScores.get(i));
            while(i+1<fuClassScores.size() && fuClassScores.get(i).getIntelligenceScore()==fuClassScores.get(i+1).getIntelligenceScore()){
                list.add(fuClassScores.get(i+1));
                i+=1;
            }
            topThreeIntelligenceScore.add(list);
            if(topThreeIntelligenceScore.size()==3){
                break;
            }
        }
        //体育前三
        fuClassScores = fuClassScores.stream().sorted(Comparator.comparing(FuClassScore::getPhysicalScore).reversed()).collect(Collectors.toList());
        for (int i = 0; i <fuClassScores.size(); i++) {
            List<FuClassScore> list = new ArrayList<>();
            list.add(fuClassScores.get(i));
            while(i+1<fuClassScores.size() && fuClassScores.get(i).getPhysicalScore()==fuClassScores.get(i+1).getPhysicalScore()){
                list.add(fuClassScores.get(i+1));
                i+=1;
            }
            topThreePhysicalScore.add(list);
            if(topThreePhysicalScore.size()==3){
                break;
            }
        }
        //美育前三
        fuClassScores = fuClassScores.stream().sorted(Comparator.comparing(FuClassScore::getAestheticScore).reversed()).collect(Collectors.toList());
        for (int i = 0; i <fuClassScores.size(); i++) {
            List<FuClassScore> list = new ArrayList<>();
            list.add(fuClassScores.get(i));
            while(i+1<fuClassScores.size() && fuClassScores.get(i).getAestheticScore()==fuClassScores.get(i+1).getAestheticScore()){
                list.add(fuClassScores.get(i+1));
                i+=1;
            }
            topThreeAestheticScore.add(list);
            if(topThreeAestheticScore.size()==3){
                break;
            }
        }
        //劳育前三
        fuClassScores = fuClassScores.stream().sorted(Comparator.comparing(FuClassScore::getLabourScore).reversed()).collect(Collectors.toList());
        for (int i = 0; i <fuClassScores.size(); i++) {
            List<FuClassScore> list = new ArrayList<>();
            list.add(fuClassScores.get(i));
            while(i+1<fuClassScores.size() && fuClassScores.get(i).getLabourScore()==fuClassScores.get(i+1).getLabourScore()){
                list.add(fuClassScores.get(i+1));
                i+=1;
            }
            topThreeLabourScore.add(list);
            if(topThreeLabourScore.size()==3){
                break;
            }
        }
        //将所有五育前三数据封装到列表中返回
        topThree.add(topThreeMoralityScore);
        topThree.add(topThreeIntelligenceScore);
        topThree.add(topThreePhysicalScore);
        topThree.add(topThreeAestheticScore);
        topThree.add(topThreeLabourScore);

        return topThree;
    }

    @Override
    public List<Pie> pie() {
        Pie student = new Pie();
        student.setName("学校学生总数");
        student.setValue(overAllOperaitons.getTotalStudentNumber());
        Pie teacher = new Pie();
        teacher.setName("学校教师总数");
        teacher.setValue(overAllOperaitons.getTotalTeacherNumber());
        Pie classes = new Pie();
        classes.setName("学校班级总数");
        classes.setValue(overAllOperaitons.getTotalClassNumber());
        List<Pie> pie = new ArrayList<>();
        Integer total = student.getValue()+ teacher.getValue()+classes.getValue();
        student.setPercent(Double.valueOf(student.getValue())/total);
        teacher.setPercent(Double.valueOf(teacher.getValue())/total);
        classes.setPercent(Double.valueOf(classes.getValue())/total);
        pie.add(student);
        pie.add(teacher);
        pie.add(classes);
        return pie;
    }

    // 获取柱状图学生信息
    @Override
    public List<StudentScore> getStudentScore(String stuGrade, String stuClass, String stuTerm) {
        //封装学生成绩信息
        List<StudentScore> studentScore = overAllOperaitons.getStudentScore(stuGrade,stuClass,stuTerm);
        for(StudentScore score:studentScore){
            if(score.getIsEnter().equals("0")){
//                System.out.println("审核");
                score.setIsEnter("true");
            }else{
                score.setIsEnter("false");
            }
            if(score.getIsReview().equals("0")){
                score.setIsReview("true");
            }else{
                score.setIsReview("false");
            }
        }
        return studentScore;
    }

    @Override
    public StudentRatio getClassStudentEnter(String stuGrade, String stuClass, String stuTerm) {
        //当前年级，班级，学期下学生总数
        Integer count = overAllOperaitons.getTotalClassStudentNumber(stuGrade,stuClass,stuTerm);
        //录入学生总数
        Integer countEnter = overAllOperaitons.getClassStudentEnterNumber(stuGrade,stuClass,stuTerm);
        StudentRatio studentRatio = new StudentRatio();
        studentRatio.setIsEntered(countEnter);
        studentRatio.setTotalNum(count);
        return studentRatio;
    }

    @Override
    public Integer updateStu(FuStudentDto stu) {
        return overAllOperaitons.updateStu(stu);
    }
}
