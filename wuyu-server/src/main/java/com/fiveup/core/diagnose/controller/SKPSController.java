package com.fiveup.core.diagnose.controller;


import com.fiveup.core.diagnose.bean.student_kpointsScore;
import com.fiveup.core.diagnose.bean.student_zhiyuScore;
import com.fiveup.core.diagnose.service.SKPSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/diagnose")
public class SKPSController {

    @Autowired
    SKPSService skpsService;
    @GetMapping("/SKPScore")
    @ResponseBody
    /*返回学生的知识点得分*/
    public List<student_kpointsScore> getstudentKpointsScoreByKey(
            @RequestParam("type") String type,
            @RequestParam("name")String name,
            @RequestParam("id") Long id){

        return skpsService.getstudentKpointsScoreByKey(type,name,id);
    }
    @GetMapping("/SKPAnalysis")
    @ResponseBody
    /*返回知识点得分分析*/
    public String getkpointsanalysis(
            @RequestParam("type") String type,
            @RequestParam("name")String name,
            @RequestParam("id") Long id){
        System.out.println(skpsService.getkpointsanalysis(type,name,id));
        return skpsService.getkpointsanalysis(type,name,id);
    }
    /*返回班级学科最大值最小值平均值*/
    @GetMapping("/classsubject")
    @ResponseBody
    public float[] getclassSubject(
            @RequestParam("subject") String subject,
            @RequestParam("grade") int  grade,
            @RequestParam("sclass") int sclass){
        List<student_zhiyuScore> list = new ArrayList<>();
        list=skpsService.getclassSubject(grade,sclass);
        return skpsService.classSubject(list,subject);
    }
    /*返回在各个学科分数段的人数*/
    @GetMapping("/classsubjectseg")
    @ResponseBody
    public int[] getclassSubjectseg(
            @RequestParam("subject") String subject,
            @RequestParam("grade") int  grade,
            @RequestParam("sclass") int sclass){
        List<student_zhiyuScore> list = new ArrayList<>();
        list=skpsService.getclassSubject(grade,sclass);
        return skpsService.classsubjectsegment(list,subject);
    }

}
