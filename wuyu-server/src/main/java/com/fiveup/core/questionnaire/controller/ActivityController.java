package com.fiveup.core.questionnaire.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fiveup.core.common.result.Result;
import com.fiveup.core.questionnaire.entity.Activity;
import com.fiveup.core.questionnaire.mapper.ActivityMapper;
import com.fiveup.core.questionnaire.service.ActivityService;
import com.fiveup.core.questionnaire.vo.ActivityVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
* 教师创建活动
* */
@RestController
@RequestMapping("/api/teacher_activity")
public class ActivityController  {


    @Autowired
    private ActivityService activityService;

    @Autowired
    private ActivityMapper mapper;


    @RequestMapping("t2")
    public Object test02(){

        HashMap<Integer, String> termInfo = new HashMap();
        termInfo.put(1,"一年级上学期");
        termInfo.put(2,"一年级下学期");
        termInfo.put(3,"二年级上学期");
        termInfo.put(4,"二年级下学期");
        termInfo.put(5,"三年级上学期");
        termInfo.put(6,"三年级下学期");
        termInfo.put(7,"四年级上学期");
        termInfo.put(8,"四年级下学期");
        termInfo.put(9,"五年级上学期");
        termInfo.put(10,"五年级下学期");
        termInfo.put(11,"六年级上学期");
        termInfo.put(12,"六年级下学期");

        //公用数据初始化
        ArrayList<String> tagCategory = new ArrayList<>();
        tagCategory.add("德育");
        tagCategory.add("智育");
        tagCategory.add("体育");
        tagCategory.add("美育");
        tagCategory.add("劳育");
        ArrayList<Integer> termCategory = new ArrayList<>();
        for (int i = 1; i <= 12 ; i++) {
            termCategory.add(i);
        }
        HashMap<String, Object> selectMap = new HashMap<>();    //查找map，用于当做MP的selectByMap参数
        HashMap<String, Object> finMap = new HashMap<>();   //接收数据的最终map
        ArrayList<HashMap> finList = new ArrayList<>(); //组合children的最终list
        //tag循环
        for (String tag : tagCategory) {
            //查找map覆盖存值
            selectMap.put("activity_target",tag);
            HashMap<String, Object> tagMap = new HashMap<>();   //tagMap存放每个tag中的数据，最终会存进finList
            ArrayList<HashMap> tagList = new ArrayList<>(); //將存好的termMap組合在一起，成為tagList
            //term循环
            for (Integer term : termCategory) {
                selectMap.put("activity_term",term);
                HashMap<String, Object> termMap = new HashMap<String, Object>(); //最后存放每个符合条件对象的activity_name字段
                ArrayList<HashMap> termList = new ArrayList<>();
                //查询循环,从数据库中按照Map查询符合条件的活动
                for (Activity activity : mapper.selectByMap(selectMap)) {
                    HashMap<String, Object> elementMap = new HashMap<>();   //将获取的每个activity_name属性，编程{id:value}键值对加入termList
                    elementMap.put("name",activity.getActivityName());
                    termList.add(elementMap);
                }
                //当活动存在时，才添加children节点
                if (termList.size() != 0){
                    termMap.put("children",termList);
                }
                //termMap添加term属性
                termMap.put("name",termInfo.get(term));
                tagList.add(termMap);
            }
            tagMap.put("children",tagList);
            //tagMap添加tag属性
            tagMap.put("name",tag);
            finList.add(tagMap);
        }
        finMap.put("children",finList);
        finMap.put("name","五育并举");
        System.out.println(JSON.toJSONString(finMap));

        return finMap;
    }


    @ApiOperation(value = "获取所有老师设置的活动")
    @GetMapping("findAll")
    public Result findAll(){
        List<Activity> list = activityService.list();
        return  Result.ok(list);
    }

    @ApiOperation(value = "条件查询带分页")
    @PostMapping("findPage/{current}/{limit}")
    public Result findPage(@PathVariable long current,
                           @PathVariable long limit,
                           @RequestBody(required = false) ActivityVO activityVo){
//        创建 Page对象，传递当前页，每页记录数
//        System.out.println(limit);
//        System.out.println(current);
        Page<Activity> page = new Page<>(current, limit);
//        构建条件
        QueryWrapper<Activity> wrapper=new QueryWrapper<>();
        String name = activityVo.getActivityName();//活动标题
        String id = activityVo.getActivityId();//活动编号
        String target = activityVo.getActivityTarget();//活动类别
        String term=activityVo.getActivityTerm();//活动学期
        if(!StringUtils.isEmpty(name)){
            wrapper.like("activity_name", activityVo.getActivityName());
        }
        if(!StringUtils.isEmpty(target)){
            wrapper.like("activity_target", activityVo.getActivityTarget());
        }
        if(!StringUtils.isEmpty(term)){
            wrapper.like("activity_term", activityVo.getActivityTerm());
        }
        if (!StringUtils.isEmpty(id)){
            wrapper.eq("activity_id", activityVo.getActivityId());
        }
//       调用方法实现分页查询
        Page<Activity> pageActivity = activityService.page(page, wrapper);
//        返回结果
        return Result.ok(pageActivity);
    }

    @ApiOperation(value = "教师创建活动")
    @PostMapping("save")
    public Result save(@RequestBody Activity activitySet){
        System.out.println(activitySet);
        boolean save = activityService.save(activitySet);
        if (save){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }
}
