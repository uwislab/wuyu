package com.fiveup.core.diagnose.controller;


import com.fiveup.core.common.result.Result;
import com.fiveup.core.diagnose.bean.Student_plan;
import com.fiveup.core.diagnose.service.SplanService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping("/diagnose")
public class SplanController {

    @Autowired
    SplanService splanService;


    /*条件分页查询获取学生计划信息*/
    @GetMapping("/getallplan")
    @ResponseBody
    public Result getallPlan(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize, Long id, String name) {

        return Result.ok(splanService.getallPlan(page, pageSize, name, id));
    }
    /*通过id获取计划信息*/
    @GetMapping("/getplanbyid")
    @ResponseBody
    public List<Student_plan> getPlanByid(@RequestParam("name") String name, @RequestParam("id") Long id) {

        return splanService.getPlanByid(name,id);
    }
}
