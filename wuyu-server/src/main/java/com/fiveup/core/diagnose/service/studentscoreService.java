package com.fiveup.core.diagnose.service;


import com.fiveup.core.diagnose.bean.*;
import com.fiveup.core.diagnose.mapper.SplanMapper;
import com.fiveup.core.diagnose.mapper.studentscoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class studentscoreService {
    @Autowired
    studentscoreMapper stMapper;

    @Autowired
    SplanMapper splanMapper;

    //返回所有五育成绩
    public List<student_gradesScore> getAllScore(){
        return  stMapper.getAllScore();
    }


    /*返回一年级五育成绩*/
    public List<student_gradesScore> getgradeoneScore(){
        return  stMapper.getgradeoneScore();
    }
    /*返回二年级五育成绩*/
    public List<student_gradesScore> getgradetwoScore(){
        return  stMapper.getgradetwoScore();
    }
    /*返回三年级五育成绩*/
    public List<student_gradesScore> getgradethreeScore(){
        return  stMapper.getgradethreeScore();
    }
    /*返回四年级五育成绩*/
    public List<student_gradesScore> getgradefourScore(){
        return  stMapper.getgradefourScore();
    }
    /*返回五年级五育成绩*/
    public List<student_gradesScore> getgradefiveScore(){
        return  stMapper.getgradefiveScore();
    }
    /*返回六年级五育成绩*/
    public List<student_gradesScore> getgradesixScore(){
        return  stMapper.getgradesixScore();
    }

    /*查找本次班级学生成绩*/
    public List<student_score> SelectScoreByClass(int grade,int sclass){
        return stMapper.SelectScoreByClassNew(grade,sclass);
    }
    /*查询本次年级成绩*/
    public List<student_score> SelectScoreByGrade(int grade){
        return stMapper.SelectScoreByGrade(grade);
    }
    /*查询上次班级成绩*/
    public List<student_score> SelectPreScoreByClass(int grade,int sclass){
        return stMapper.SelectPreScoreByClass(grade,sclass);
    }
    /*查询上次年级成绩*/
    public List<student_score> SelectPreScoreByGrade(int grade){
        return stMapper.SelectPreScoreByGrade(grade);
    }
    /*查询学生个人成绩*/
    public List<student_score> SelectScoreByKey(String name,Long id){
        return stMapper.SelectScoreByKey(name,id);
    }
    /*返回班级年级*/
    public student_classgrade getClass(String name, Long id){
        return stMapper.getClass(name,id);
    }
    /*计算平均分*/
    public static float[] avaragescore(List<student_score> listscore) {
        int allDeyu=0;
        int allZhiyu=0;
        int allTiyu=0;
        int allMeiyu=0;
        int allLaoyu=0;
        float avgDeyu=0;
        float avgZhiyu=0;
        float avgTiyu=0;
        float avgMeiyu=0;
        float avgLaoyu=0;
        for (int i = 0; i < listscore.size(); i++) {
            allDeyu = allDeyu + listscore.get(i).getsDeyu();
            allZhiyu = allZhiyu + listscore.get(i).getsZhiyu();
            allMeiyu = allMeiyu + listscore.get(i).getsMeiyu();
            allTiyu = allTiyu + listscore.get(i).getsTiyu();
            allLaoyu = allLaoyu + listscore.get(i).getsLaoyu();
        }
        avgDeyu=(float)allDeyu/listscore.size();
        avgZhiyu=(float)allZhiyu/listscore.size();
        avgTiyu=(float)allTiyu/listscore.size();
        avgMeiyu=(float)allMeiyu/listscore.size();
        avgLaoyu=(float)allLaoyu/listscore.size();
        float[] list = new float[5];
        list[0]=avgDeyu;
        list[1]=avgZhiyu;
        list[2]=avgTiyu;
        list[3]=avgMeiyu;
        list[4]=avgLaoyu;
        return list;
    }
    //标准差σ=sqrt(s^2)
    public static float[] avaragescoreNew(List<student_score> listscore) {
        float[] list = new float[5];
        if (listscore == null || listscore.isEmpty()) {
            // 返回全为 0 的数组或者其他默认值
            return list; // 此时 list 中的元素默认都是 0.0
        }

        int allDeyu=0;
        int allZhiyu=0;
        int allTiyu=0;
        int allMeiyu=0;
        int allLaoyu=0;

        for (int i = 0; i < listscore.size(); i++) {
            allDeyu = allDeyu + listscore.get(i).getsDeyu();
            allZhiyu = allZhiyu + listscore.get(i).getsZhiyu();
            allMeiyu = allMeiyu + listscore.get(i).getsMeiyu();
            allTiyu = allTiyu + listscore.get(i).getsTiyu();
            allLaoyu = allLaoyu + listscore.get(i).getsLaoyu();
        }

        list[0]=(float)allDeyu/listscore.size();
        list[1]=(float)allZhiyu/listscore.size();
        list[2]=(float)allTiyu/listscore.size();
        list[3]=(float)allMeiyu/listscore.size();
        list[4]=(float)allLaoyu/listscore.size();

        return list;
    }
    public static double StandardDiviation(int[] x) {
        int m=x.length;
        int sum=0;
        for(int i=0;i<m;i++){//求和
            sum+=x[i];
        }
        float dAve=sum/m;//求平均值
        float dVar=0;
        for(int i=0;i<m;i++){//求方差
            dVar+=(x[i]-dAve)*(x[i]-dAve);
        }
        return Math.sqrt(dVar/m);
    }
    public float[] gradeClasssAvgcore(List<student_score> listGrade,List<student_score> listClass){
        float[] avgscore= new float[10];
        float[] avgClass= new float[5];/*班级平均成绩数组*/
        float[] avgGrade= new float[5];/*年级平均成绩数组*/
        avgClass=studentscoreService.avaragescore(listClass);
        avgGrade=studentscoreService.avaragescore(listGrade);
        for(int i=0;i<5;i++){
            avgscore[i] = avgClass[i];
        }
        for(int i=0;i<5;i++){
            avgscore[i+5] = avgGrade[i];
        }
        return avgscore;
    }
    /*得到班级与年级平均成绩对比评价*/
    public String getclassAnalyse(float[] avgscore,List<student_score> listClass){
        String analyse="";
        int overAvgFlog=0;/*计数高于平均分的成绩*/
        int activityFlog=0;/*计数除了智育的活动*/
        System.out.println(avgscore);
        if(avgscore[0]<avgscore[5]){
            analyse=analyse+"班级德育平均成绩低于年级平均成绩" +String.format("%.2f", avgscore[5]-avgscore[0])+"分;";
        }
        else{
            analyse=analyse+"班级德育平均成绩高于年级平均成绩" +String.format("%.2f", avgscore[0]-avgscore[5])+"分;";
            overAvgFlog+=1;
            activityFlog+=1;
        }
        if(avgscore[1]<avgscore[6]){
            analyse=analyse+"班级智育平均成绩低于年级平均成绩" +String.format("%.2f", avgscore[6]-avgscore[1])+"分;";
        }
        else{
            analyse=analyse+"班级智育平均成绩高于年级平均成绩" +String.format("%.2f", avgscore[1]-avgscore[6])+"分;";
            overAvgFlog+=1;
        }
        if(avgscore[2]<avgscore[7]){
            analyse=analyse+"班级体育平均成绩低于年级平均成绩" +String.format("%.2f", avgscore[7]-avgscore[2])+"分;";
        }
        else{
            analyse=analyse+"班级体育平均成绩高于年级平均成绩" +String.format("%.2f", avgscore[2]-avgscore[7])+"分;";
            overAvgFlog+=1;
            activityFlog+=1;
        }
        if(avgscore[3]<avgscore[8]){
            analyse=analyse+"班级美育平均成绩低于年级平均成绩" +String.format("%.2f", avgscore[8]-avgscore[3])+"分;";
        }
        else{
            analyse=analyse+"班级美育平均成绩高于年级平均成绩" +String.format("%.2f", avgscore[3]-avgscore[8])+"分;";
            overAvgFlog+=1;
            activityFlog+=1;
        }
        if(avgscore[4]<avgscore[9]){
            analyse=analyse+"班级劳育平均成绩低于年级平均成绩" +String.format("%.2f", avgscore[9]-avgscore[4])+"分;";
        }
        else{
            analyse=analyse+"班级劳育平均成绩高于年级平均成绩" +String.format("%.2f", avgscore[4]-avgscore[9])+"分;";
            overAvgFlog+=1;
            activityFlog+=1;
        }
        /*分析班内成绩分化情况*/
        int wellDeyu=0;
        int badDeyu=0;
        int wellZhiyu=0;
        int badZhiyu=0;
        int wellTiyu=0;
        int badTiyu=0;
        int wellMeiyu=0;
        int badMeiyu=0;
        int wellLaoyu=0;
        int badLaoyu=0;
        /*判断德育成绩的分布*/
        for(int i=0;i<listClass.size();i++){
            if(listClass.get(i).getsDeyu()>=90){
                wellDeyu+=1;
            }
            if(listClass.get(i).getsDeyu()<70){
                badDeyu+=1;
            }
        }
        /*判断智育成绩的分布*/
        for(int i=0;i<listClass.size();i++){
            if(listClass.get(i).getsZhiyu()>=90){
                wellDeyu+=1;
            }
            if(listClass.get(i).getsZhiyu()<70){
                badDeyu+=1;
            }
        }
        /*判断体育成绩的分布*/
        for(int i=0;i<listClass.size();i++){
            if(listClass.get(i).getsTiyu()>=90){
                wellDeyu+=1;
            }
            if(listClass.get(i).getsTiyu()<70){
                badDeyu+=1;
            }
        }
        /*判断美育成绩的分布*/
        for(int i=0;i<listClass.size();i++){
            if(listClass.get(i).getsMeiyu()>=90){
                wellDeyu+=1;
            }
            if(listClass.get(i).getsMeiyu()<70){
                badDeyu+=1;
            }
        }
        /*判断劳育成绩的分布*/
        for(int i=0;i<listClass.size();i++){
            if(listClass.get(i).getsLaoyu()>=90){
                wellDeyu+=1;
            }
            if(listClass.get(i).getsLaoyu()<70){
                badDeyu+=1;
            }
        }
        /*计算占比*/
        int []floglist = new int[5];
        analyse=analyse+"班级内：";
        if((wellDeyu/listClass.size())>=0.2&&(badDeyu/listClass.size())>=0.2){
            floglist[0]+=1;
        }
        if((wellZhiyu/listClass.size())>=0.2&&(badZhiyu/listClass.size())>=0.2){
            floglist[1]+=1;
        }
        if((wellTiyu/listClass.size())>=0.2&&(badTiyu/listClass.size())>=0.2){
            floglist[2]+=1;
        }
        if((wellMeiyu/listClass.size())>=0.2&&(badMeiyu/listClass.size())>=0.2){
            floglist[3]+=1;
        }
        if((wellLaoyu/listClass.size())>=0.2&&(badLaoyu/listClass.size())>=0.2){
            floglist[4]+=1;
        }
        /*添加分析*/
        int unstableflog=0;
        for(int i=0;i<floglist.length;i++){
            if(floglist[i]==1){
                if(i==0){
                    analyse=analyse+"德育成绩，";
                    unstableflog+=1;
                }
                else if(i==1){
                    analyse=analyse+"智育成绩，";
                    unstableflog+=1;
                }
                else if(i==2){
                    analyse=analyse+"体育成绩，";
                    unstableflog+=1;
                }
                else if(i==3){
                    analyse=analyse+"美育成绩，";
                    unstableflog+=1;
                }
                else{
                    analyse=analyse+"劳育成绩，";
                    unstableflog+=1;
                }
            }
        }
        if(unstableflog>0){
            analyse=analyse.substring(0,analyse.length()-1);
            analyse=analyse+"分化较大。";
        }else{
            analyse=analyse+"成绩分布较为均匀，差距较小。";
        }
/*        analyse=analyse.substring(0,analyse.length()-1);
        analyse=analyse+"分布均匀。";*/
        if(activityFlog>=2){
            analyse=analyse+"继续保持参加活动的热情,";
        }
        else{
            analyse=analyse+"需要积极参加学校活动,";
        }
        if(overAvgFlog==0){
            analyse=analyse+"班级平均成绩全面落后于年级平均成绩，需要继续努力。";
        }
        else if(overAvgFlog==1||overAvgFlog==2){
            analyse=analyse+"保持优势，提高劣势，继续加油。";
        }
        else if(overAvgFlog==3||overAvgFlog==4){
            analyse=analyse+"大多数科目在年级平均分以上，继续保持。";
        }
        else{
            analyse=analyse+"班级平均成绩全面高于年级平均分，十分优秀，请继续保持。";
        }

        return analyse;
    }
    /*计算相应学生班级的平均成绩*/
    public float[] classAvgscore(List<student_score> listClass){
        float[] avgClass= new float[5];
        avgClass=studentscoreService.avaragescore(listClass);
        return avgClass;
    }
    /*得到学生成绩计划完成度*/
    public float[] studentPlanComplition(String name,Long id,List<student_score> studentscore){
        Student_plan  listplan = new Student_plan();
        listplan=splanMapper.getPlanByidone(name,id);
        float[] targetCompletion = new float[5];
        targetCompletion[0]=(float)studentscore.get(0).getsDeyu()/listplan.getpDeyu()*100;
        targetCompletion[1]=(float)studentscore.get(0).getsZhiyu()/listplan.getpZhiyu()*100;
        targetCompletion[2]=(float)studentscore.get(0).getsTiyu()/listplan.getpTiyu()*100;
        targetCompletion[3]=(float)studentscore.get(0).getsMeiyu()/listplan.getpMeiyu()*100;
        targetCompletion[4]=(float)studentscore.get(0).getsLaoyu()/listplan.getpLaoyu()*100;
        /*超额完成计划给100*/
        for(int i=0;i<targetCompletion.length;i++){
            if(targetCompletion[i]>100){
                targetCompletion[i]=100;
            }
        }
        return targetCompletion;
    }
    /*得到学生在班级，年级中的总分排名*/
    public int ScoreRank( List<student_score> listscore,int sumScore){
        int[] listScoreSum = new int[listscore.size()];
        /*计算总分*/
        for(int i=0;i<listscore.size();i++){
            listScoreSum[i]=listscore.get(i).getsDeyu()+listscore.get(i).getsZhiyu()+
                    listscore.get(i).getsTiyu()+listscore.get(i).getsMeiyu()+
                    listscore.get(i).getsLaoyu();
        }
        /*冒泡排序*/
        for(int i=0;i< listScoreSum.length-1;i++) {
            for(int j=0;j<listScoreSum.length-i-1;j++) {
                if(listScoreSum[j]<listScoreSum[j+1]) {
                    int temp=listScoreSum[j];
                    listScoreSum[j]=listScoreSum[j+1];
                    listScoreSum[j+1]=temp;
                }

            }
        }
        int flog=0;
        /*将排序后的数组学生成绩对比，得其排名*/
        for(int i=0;i<listScoreSum.length;i++){
            if(listScoreSum[i]==sumScore){
                flog= i+1;
            }
        }
        return flog;
    }
    /*得到学生个人学习评价*/
    public String studentscoreAnalyse(int classRankCompare,int gradeRankCompare,int ClassScoreRank,int GradeScoreRank,int sumScore,List<student_score> studentscore,float[] classAvgscore,float[] planComplition,Student_plan splan){
        /*获取学生计划进行评判*/
        splan.getpPlan();
        String analyse="";
        int progressflag=0;
        int subjectflag=0;
        int classflag=0;
        int gradeflag=0;

        if(classRankCompare<0){
            classflag=1;
        }
        else if(classRankCompare==0){
            classflag=0;
        }
        else{

            classflag=-1;
        }
        if(gradeRankCompare<0){
            gradeflag=1;
        }
        else if(gradeRankCompare==0){
            gradeflag=0;
        }
        else{

            gradeflag=-1;
        }
        /*根据班级排名，年级排名的上升下降生成诊断结果*/
        if(classflag>0&&gradeflag>0){
            analyse=analyse+"书山有路勤为径,学海无涯苦作舟,通过这段时间的努力成长与发展" +
                    "五育发展上有很大进步，需要继续保持！";
        }

        else if(classflag==0&&gradeflag==0){
            analyse=analyse+"成长与发展需要用心钻研，有时候短板是发展中不可忽视的，要均衡发展，才能更上一层楼。";
        }
        else{
            analyse=analyse+"学如逆水行舟，不进则退；心似平原野马，易收难放!希望能认真总结，发奋图强。";
        }
        analyse=analyse+"与上次五育发展,班级五育发展相比:";
        if((studentscore.get(0).getsDeyu()-studentscore.get(1).getsDeyu())>0){
            analyse=analyse+"德育发展有进步,";
        }
        else if((studentscore.get(0).getsDeyu()-studentscore.get(1).getsDeyu())==0){
            analyse=analyse+"德育发展保持不变，";
        }
        else{
            analyse=analyse+"德育发展有退步;";
        }
        if((studentscore.get(0).getsZhiyu()-studentscore.get(1).getsZhiyu())>0){
            analyse=analyse+"智育发展有进步,";

        }
        else if((studentscore.get(0).getsZhiyu()-studentscore.get(1).getsZhiyu())==0){
            analyse=analyse+"智育发展保持不变，";

        }
        else{
            analyse=analyse+"智育发展有退步;";

        }
        if((studentscore.get(0).getsTiyu()-studentscore.get(1).getsTiyu())>0){
            analyse=analyse+"体育发展有进步,";

        }
        else if((studentscore.get(0).getsTiyu()-studentscore.get(1).getsTiyu())==0){
            analyse=analyse+"智育发展保持不变，";

        }
        else{
            analyse=analyse+"体育发展有退步;";

        }
        if((studentscore.get(0).getsMeiyu()-studentscore.get(1).getsMeiyu())>0){
            analyse=analyse+"美育发展有进步,";

        }
        else if((studentscore.get(0).getsMeiyu()-studentscore.get(1).getsMeiyu())==0){
            analyse=analyse+"美育发展保持不变，";

        }
        else{
            analyse=analyse+"美育发展有退步;";

        }
        if((studentscore.get(0).getsLaoyu()-studentscore.get(1).getsLaoyu())>0){
            analyse=analyse+"劳育发展有进步,";

        }
        else if((studentscore.get(0).getsLaoyu()-studentscore.get(1).getsLaoyu())==0){
            analyse=analyse+"劳育发展保持不变，";

        }
        else{
            analyse=analyse+"劳育发展有退步;";

        }

        /*当总分达到450分以上时的评价*/
        if(sumScore>450){
            analyse=analyse+"五育各项发展都十分优秀，善于利用时间去学习的你能够在学习方面出类拔萃，" +
                    "思想上有上进性，热爱学习，千万不能骄傲自大，" +
                    "需要继续保持学习状态哦!";
        }
        /*计划完成度的分析*/
        int overcomplete=0;
        int wellcomplete=0;
        int notcomplete=0;
        for(int i=0;i<planComplition.length;i++){
            if(planComplition[i]>=100){
                overcomplete+=1;
            }
            else if(planComplition[i]<100&&planComplition[i]>=95){
                wellcomplete+=1;
            }
            else{
                notcomplete+=1;
            }
        }
        if(overcomplete>0){
            analyse=analyse+overcomplete+"项五育发展完成率100%，";
        }
        if(wellcomplete>0) {
            analyse = analyse +wellcomplete+ "项完成度达优秀。";
        }
        analyse=analyse.substring(0,analyse.length()-1);
        analyse=analyse+"。";
        /*计算成绩的方差，得到稳定性*/
        int[] listDeyu = new int[2];
        listDeyu[0]=studentscore.get(0).getsDeyu();
        listDeyu[1]=studentscore.get(1).getsDeyu();
        int[] listZhiyu = new int[2];
        listZhiyu[0]=studentscore.get(0).getsZhiyu();
        listZhiyu[1]=studentscore.get(1).getsZhiyu();
        int[] listTiyu = new int[2];
        listTiyu[0]=studentscore.get(0).getsTiyu();
        listTiyu[1]=studentscore.get(1).getsTiyu();
        int[] listMeiyu = new int[2];
        listMeiyu[0]=studentscore.get(0).getsMeiyu();
        listMeiyu[1]=studentscore.get(1).getsMeiyu();
        int[] listLaoyu = new int[2];
        listLaoyu[0]=studentscore.get(0).getsLaoyu();
        listLaoyu[1]=studentscore.get(1).getsLaoyu();
        double[] scoreStandardDiviation= new double[5];
        scoreStandardDiviation[0]=studentscoreService.StandardDiviation(listDeyu);
        scoreStandardDiviation[1]=studentscoreService.StandardDiviation(listZhiyu);
        scoreStandardDiviation[2]=studentscoreService.StandardDiviation(listTiyu);
        scoreStandardDiviation[3]=studentscoreService.StandardDiviation(listMeiyu);
        scoreStandardDiviation[4]=studentscoreService.StandardDiviation(listLaoyu);
        int stabflag=0;
        for(int i=0;i<scoreStandardDiviation.length;i++){
            if(scoreStandardDiviation[i]<5){
                stabflag+=1;
            }
        }
        if(stabflag==5){
            analyse=analyse+"各项五育发展十分稳定。";
        }
        else{
            if(scoreStandardDiviation[0]>5.0){
                analyse=analyse+"德育发展，";
            }
            if(scoreStandardDiviation[1]>5.0){
                analyse=analyse+"智育发展，";
            }
            if(scoreStandardDiviation[2]>5.0){
                analyse=analyse+"体育发展，";
            }
            if(scoreStandardDiviation[3]>5.0){
                analyse=analyse+"美育发展，";
            }
            if(scoreStandardDiviation[4]>5.0){
                analyse=analyse+"劳育发展，";
            }
            analyse=analyse.substring(0,analyse.length()-1);
            analyse=analyse+"波动幅度较大。各项五育发展需要保持稳定，稳住才能有更好的进步。";
        }
        return analyse;
    }

    public int[] getGrades() {
        return stMapper.getGrades();
    }

    public int[] getClasses(int grade) {
        return stMapper.getClasses(grade);
    }

    public List<GradeScoreBean> getGradeScoreBean(Integer grade) {
        return stMapper.getGradeScoreBygrade(grade);

    }
}
