package com.fiveup.core.diagnose.service;


import com.fiveup.core.diagnose.bean.student_kpointsScore;
import com.fiveup.core.diagnose.bean.student_zhiyuScore;
import com.fiveup.core.diagnose.mapper.SKPSMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SKPSService {

    @Autowired
    SKPSMapper skpsMapper;
    public List<student_kpointsScore> getstudentKpointsScoreByKey(String type, String name, Long id){
        return skpsMapper.getstudentKpointsScoreByKey(type,name,id);
    }
    /**/
    public List<student_zhiyuScore> getclassSubject(int grade, int sclass){
        List<student_zhiyuScore> list = new ArrayList<>();
        return skpsMapper.getclassSubject(grade,sclass);
    }
    public float[] classSubject(List<student_zhiyuScore> list,String subject){
        /*计算对应学科的平均分，最大值最小值*/
        int MaxScore=0;
        int MinScore=999;
        int sumScore=0;
        float AvgScore=0;
        float[] sublist = new float[3];
        for(int i=0;i<sublist.length;i++){
            sublist[i]=0;
        }
        /*得到的学生列表为空，返回空*/
        if(list.size()==0){
            return sublist;
        }
        if(subject.equals("chinese")){/*判断字符串是否相等*/
            /*找最大值最小值，总分*/
            for(int i=0;i<list.size();i++){
                if(list.get(i).getChinese()>MaxScore){
                    MaxScore=list.get(i).getChinese();
                }
                if(list.get(i).getChinese()<MinScore){
                    MinScore=list.get(i).getChinese();
                }
                sumScore+=list.get(i).getChinese();
            }
            AvgScore=(float)sumScore/list.size();
        }
        else if(subject.equals("math")){
            /*找最大值最小值，总分*/
            for(int i=0;i<list.size();i++){
                if(list.get(i).getMath()>MaxScore){
                    MaxScore=list.get(i).getMath();
                }
                if(list.get(i).getMath()<MinScore){
                    MinScore=list.get(i).getMath();
                }
                sumScore+=list.get(i).getMath();
            }
            AvgScore=(float)sumScore/list.size();

        }else{
            /*找最大值最小值，总分*/
            for(int i=0;i<list.size();i++){
                if(list.get(i).getEnglish()>MaxScore){
                    MaxScore=list.get(i).getEnglish();
                }
                if(list.get(i).getEnglish()<MinScore){
                    MinScore=list.get(i).getEnglish();
                }
                sumScore+=list.get(i).getEnglish();
            }
            AvgScore=(float)sumScore/list.size();
        }
        sublist[0]=MaxScore;
        sublist[1]=MinScore;
        sublist[2]=AvgScore;
        return sublist;
    }
    /*统计在各个分数段的人数*/
    public int[] classsubjectsegment(List<student_zhiyuScore> list,String subject){
        int[] listseg = new int[5];
        if(subject.equals("chinese")){
            for(int i=0;i<list.size();i++){
                if(list.get(i).getChinese()>=90&&list.get(i).getChinese()<=100){
                    listseg[0]+=1;
                }
                else if(list.get(i).getChinese()>=80&&list.get(i).getChinese()<90){
                    listseg[1]+=1;
                }
                else if(list.get(i).getChinese()>=70&&list.get(i).getChinese()<80){
                    listseg[2]+=1;
                }
                else if(list.get(i).getChinese()>=60&&list.get(i).getChinese()<70){
                    listseg[3]+=1;
                }
                else{
                    listseg[4]+=1;
                }
            }
        }
        else if(subject.equals("math")){
            for(int i=0;i<list.size();i++){
                if(list.get(i).getMath()>=90&&list.get(i).getMath()<=100){
                    listseg[0]+=1;
                }
                else if(list.get(i).getMath()>=80&&list.get(i).getMath()<90){
                    listseg[1]+=1;
                }
                else if(list.get(i).getMath()>=70&&list.get(i).getMath()<80){
                    listseg[2]+=1;
                }
                else if(list.get(i).getMath()>=60&&list.get(i).getMath()<70){
                    listseg[3]+=1;
                }
                else{
                    listseg[4]+=1;
                }
            }
        }
        else{
            for(int i=0;i<list.size();i++){
                if(list.get(i).getEnglish()>=90&&list.get(i).getEnglish()<=100){
                    listseg[0]+=1;
                }
                else if(list.get(i).getEnglish()>=80&&list.get(i).getEnglish()<90){
                    listseg[1]+=1;
                }
                else if(list.get(i).getEnglish()>=70&&list.get(i).getEnglish()<80){
                    listseg[2]+=1;
                }
                else if(list.get(i).getEnglish()>=60&&list.get(i).getEnglish()<70){
                    listseg[3]+=1;
                }
                else{
                    listseg[4]+=1;
                }
            }
        }
        return listseg;
    }
    /*得到知识点评价*/
    public String getkpointsanalysis(String type, String name, Long id){
        List<student_kpointsScore> list = new ArrayList<student_kpointsScore>();
        list=skpsMapper.getstudentKpointsScoreByKey(type,name,id);
        String analysis="";
        int allscore=0;
        for( int i=0;i< list.size();i++){
            allscore=allscore+list.get(i).getKsScore();
        }
        if(allscore==100){
            analysis=analysis+"得了优，十分优秀，请继续保持!";
        }else{

            for( int i=0;i< list.size();i++){
                analysis =analysis+list.get(i).getkName()+"所占比例为: ";
                analysis =analysis+list.get(i).getkProportion()+"%；";
            }
            /*计算失分*/
            int[] losescore = new int[list.size()];
            float[] losescoreP = new float[list.size()];
            for( int i=0;i< list.size();i++){
                losescore[i]=list.get(i).getkProportion()-list.get(i).getKsScore();
                losescoreP[i] = (float)(list.get(i).getkProportion()-list.get(i).getKsScore())/list.get(i).getkProportion();
            }
            analysis=analysis+"其中";
            /*计数满分知识点*/
            for( int i=0;i< losescore.length;i++){
                if(losescore[i]==0) {
                    analysis=analysis+list.get(i).getkName()+",";
                }
            }
            analysis=analysis.substring(0,analysis.length()-1);
            analysis=analysis+"知识点掌握不错，请继续保持"+",";
            int[] maxloseflog=new int[list.size()];
            int[] maxlosepflog=new int[list.size()];
            int maxlose=0;
            float maxloseP=0;
            for( int i=0;i< losescore.length;i++){
                if(losescore[i]>=maxlose) {
                    maxlose = losescore[i];
                }
                if(losescoreP[i]>=maxloseP) {
                    maxloseP = losescoreP[i];
                }
            }
            for( int i=0;i< losescore.length;i++){
                if(losescore[i]==maxlose) {
                    analysis=analysis+list.get(i).getkName()+",";
                }
            }
            analysis=analysis.substring(0,analysis.length()-1);
            analysis=analysis+"失分较多，仍有进步空间"+',';
            for( int i=0;i< losescore.length;i++){
                if(losescoreP[i]==maxloseP) {
                    analysis=analysis+list.get(i).getkName()+",";
                }
            }
            analysis=analysis.substring(0,analysis.length()-1);
            analysis=analysis+"是知识点的薄弱部分，需要加强练习。";
        }
        return analysis;
    }
}
