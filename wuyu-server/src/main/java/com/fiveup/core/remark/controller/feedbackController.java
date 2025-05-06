package com.fiveup.core.remark.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fiveup.core.remark.dto.FeedBackQueryRequest;
import com.fiveup.core.remark.entity.*;
import com.fiveup.core.remark.mapper.*;
import com.fiveup.core.remark.service.*;
import com.fiveup.core.remark.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/feedback")
public class feedbackController {
    @Autowired
    feedbackMapper feedbackMapper;
    @Autowired
    re_studentMapper studentMapper;
    //    @PostMapping("/save")
//    public void save(@RequestBody feedback feedback){
//        System.out.println(feedback);
//        feedbackMapper.insert(feedback);
//    }
    @GetMapping("/save")
    public void save(@RequestParam("id")Integer Id,
                     @RequestParam("rate") Integer Rate,
                     @RequestParam("fobject") String Fobject,
                     @RequestParam("hobject") String Hobject,
                     @RequestParam("feedback") String Feedback,
                     @RequestParam("sid") Integer SId,
                     @RequestParam("tid") Integer Tid,
                     @RequestParam("name") String Name){
        feedback feedback = new feedback();
        feedback.setId(Id);
        feedback.setRate(Rate);
        feedback.setFobject(Fobject);
        feedback.setHobject(Hobject);
        feedback.setFeedback(Feedback);
        feedback.setSid(SId);
        feedback.setTid(Tid);
        feedback.setName(Name);
        System.out.println(feedback);
        feedbackMapper.insert(feedback);
    }
    @GetMapping("/edit")
    public void edit(@RequestParam("sid") Integer Sid,
                     @RequestParam("tid") Integer Tid,
                     @RequestParam("feedback_id") Integer Fid,
                     @RequestParam("name") String Name,
                     @RequestParam("rate") Integer Rate,
                     @RequestParam("fobject") String Fobject,
                     @RequestParam("hobject") String Hobject,
                     @RequestParam("feedback") String Feedback){
//        feedbackMapper.updateById(feedback);
        System.out.println("start update，这是修改后的数据");
        System.out.println(Sid);
        System.out.println(Rate);
        System.out.println(Fobject);
        System.out.println(Hobject);
        System.out.println(Feedback);
//        feedbackMapper.updatefeedback(Sid,Rate,Fobject,Hobject,Feedback);
        feedback feedback = new feedback();
        feedback.setId(Fid);
        feedback.setRate(Rate);
        feedback.setFobject(Fobject);
        feedback.setHobject(Hobject);
        feedback.setFeedback(Feedback);
        feedback.setSid(Sid);
        feedback.setTid(Tid);
        feedback.setName(Name);
        System.out.println("要修改的对象为：");
        System.out.println(feedback);
        feedbackMapper.updateById(feedback);
        System.out.println("update done");
    }

    @GetMapping("/getfeedback")
    public feedback findfeedback(@RequestParam("sid") Integer Sid){
//        feedback feedback = feedbackMapper.selectById(Id);
        feedback feedback = feedbackMapper.findfeedback(Sid);
        System.out.println("数据库找反馈");
        System.out.println(feedback);
        return feedback;
    }
    @GetMapping("/findnumber")
    public HashMap<String,Integer> findnumber(@RequestParam("tid")Integer Tid){
        HashMap<String,Object> map = new HashMap<>();
        HashMap<String,Integer> map1;
        map.put("tid",Tid);
        List<feedback> feedbacks = feedbackMapper.selectByMap(map);
        System.out.println("找第一张图数据");
        map1 = createstatisticsService.createstatistic(feedbacks);
        return map1;
    }
    @PostMapping("/pageset")
    public Result<?> pageset(@RequestBody FeedBackQueryRequest feedBackQueryRequest){
     Integer pageNum = feedBackQueryRequest.getPageNum();
     Integer pageSize = feedBackQueryRequest.getPageSize();
     String search = feedBackQueryRequest.getSearch();

     LambdaQueryWrapper<feedback> wrapper = Wrappers.<feedback>lambdaQuery();
     if(StrUtil.isNotBlank(search)){
            wrapper.like(feedback::getFeedback,search)
                    .or()
                    .like(feedback::getName,search);

     }
        Page<feedback> feedbackPage = feedbackMapper.selectPage(new Page<>(pageNum,pageSize), wrapper);
        return Result.success(feedbackPage);
    }
    @GetMapping("/findhobject")
    public HashMap<String,Integer> findhobject(@RequestParam("tid") Integer Tid){
        HashMap<String,Object> map = new HashMap<>();
        HashMap<String,Integer> map1;
        map.put("tid",Tid);
        List<feedback> feedbacks = feedbackMapper.selectByMap(map);
        map1 = createstatisticsService.findchart2(feedbacks);
        System.out.println("找第二张图数据");
        return map1;
    }
    @GetMapping("/findaverage")
    public HashMap<String, Double> findaverage(@RequestParam("tid") Integer Tid){
        HashMap<String,Object> map = new HashMap<>();
        HashMap<String,Double> map1;
        map.put("tid",Tid);
        List<feedback> feedbacks = feedbackMapper.selectByMap(map);
        map1 = createstatisticsService.findaverage(feedbacks);
        System.out.println("找第三张图数据");
        return map1;
    }
    @GetMapping("/iffeedback")
    public HashMap<String, Integer> iffeedback(@RequestParam("sid") Integer Sid){
        HashMap<String,Integer> map = new HashMap<>();
        int flag = 0;
        feedback feedback = feedbackMapper.findfeedback(Sid);
        if(feedback == null){
            flag = 1;
        }
        map.put("flag",flag);
        return map;
    }
}
