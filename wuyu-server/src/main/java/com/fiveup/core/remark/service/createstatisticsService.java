package com.fiveup.core.remark.service;
import com.fiveup.core.remark.entity.*;
import com.fiveup.core.remark.mapper.*;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class createstatisticsService {
    public static HashMap<String,Integer> createstatistic(List<feedback> feedbacks){
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        int v_1 = 0;
        int i_1 = 0;
        int s_1 = 0;
        int a_1 = 0;
        int l_1 = 0;
//        for(int i = 0; i<feedbacks.size();i++){
//            System.out.println(feedbacks.get(i));
//        }
        for (com.fiveup.core.remark.entity.feedback feedback : feedbacks) {
//            System.out.println(feedback.getFobject());
            if(Objects.equals(feedback.getFobject(), "德育")){
                v_1 += 1;
            }
            else if(Objects.equals(feedback.getFobject(), "智育")){
                i_1 += 1;
            }
            else if(Objects.equals(feedback.getFobject(), "体育")){
                s_1 += 1;
            }
            else if(Objects.equals(feedback.getFobject(), "美育")){
                a_1 += 1;
            }
            else {
                l_1 += 1;
            }
        }
        map.put("virtue",v_1);
        map.put("intelligence",i_1);
        map.put("sports",s_1);
        map.put("art",a_1);
        map.put("labor",l_1);
        return map;

    }
    public static HashMap<String,Integer> findchart2(List<feedback> feedbacks){
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        int v_1 = 0;
        int i_1 = 0;
        int s_1 = 0;
        int a_1 = 0;
        int l_1 = 0;
        for (com.fiveup.core.remark.entity.feedback feedback : feedbacks) {

            if(Objects.equals(feedback.getHobject(), "德育")){
                v_1 += 1;
            }
            else if(Objects.equals(feedback.getHobject(), "智育")){
                i_1 += 1;
            }
            else if(Objects.equals(feedback.getHobject(), "体育")){
                s_1 += 1;
            }
            else if(Objects.equals(feedback.getHobject(), "美育")){
                a_1 += 1;
            }
            else {
                l_1 += 1;
            }
        }
        map.put("virtue",v_1);
        map.put("intelligence",i_1);
        map.put("sports",s_1);
        map.put("art",a_1);
        map.put("labor",l_1);
        return map;
    }
    public static HashMap<String, Double> findaverage(List<feedback> feedbacks){
        HashMap<String, Double> map = new HashMap<String, Double>();
        double p_1 = 0;
        double p_2 = 0;
        double p_3 = 0;
        double p_4 = 0;
        double p_5 = 0;
        double ave = 0;
        for (com.fiveup.core.remark.entity.feedback feedback : feedbacks) {
            if (feedback.getRate() == 5){
                p_5 += 1;
            }
            else if(feedback.getRate() == 4){
                p_4 += 1;
            }
            else if(feedback.getRate() == 3){
                p_3 += 1;
            }
            else if(feedback.getRate() == 2){
                p_2 += 1;
            }
            else if(feedback.getRate() == 1){
                p_1 += 1;
            }
        }
        ave = (5 * p_5 + 4 * p_4 + 3*p_3 + 2* p_2 + p_1)/ feedbacks.size();
        map.put("five",p_5);
        map.put("four",p_4);
        map.put("three",p_3);
        map.put("two",p_2);
        map.put("one",p_1);
        map.put("ave",ave);
        return map;
    }
}
