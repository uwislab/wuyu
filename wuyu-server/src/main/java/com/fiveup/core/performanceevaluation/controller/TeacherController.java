package com.fiveup.core.performanceevaluation.controller;

import com.alibaba.fastjson.JSON;
import com.fiveup.core.performanceevaluation.bean.SubjectWeight;
import com.fiveup.core.performanceevaluation.bean.Teacher;
import com.fiveup.core.performanceevaluation.service.SubjectScoreWeightService;
import com.fiveup.core.performanceevaluation.service.SubjectWeightService;
import com.fiveup.core.performanceevaluation.service.TeacherService;
import com.fiveup.core.performanceevaluation.utils.Result;
import com.fiveup.core.performanceevaluation.vo.SubjectScoreWeightVO;
import com.fiveup.core.performanceevaluation.vo.SubjectWeightVO;
import jnr.ffi.annotations.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 处理与老师相关的请求
 */
@Controller
@CrossOrigin
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private SubjectScoreWeightService subjectScoreWeightService;

    @Autowired
    private SubjectWeightService subjectWeightService;


    /**
     * 获取所有记录
     * @return
     */
    @ResponseBody
    @GetMapping("/list")
    public String getAllList() {
        Result result = new Result();
        // 查询所有老师数据信息
        List<Teacher> all = teacherService.getAll();
        for(Teacher i :all){
            System.out.println(i);
        }
        // 将老师的密码去除
        for (Teacher teacher : all) {
            teacher.setPassword("");
        }
        result.setCode(600);
        result.setData(all);
        result.setMsg("查询成功！");
        return JSON.toJSON(result).toString();
    }

    /**
     * 获取未设置权重的老师
     * @return
     */
    @ResponseBody
    @RequestMapping("/weight")
    public String getNotInWeight() {
        Result result = new Result();

        // 查询所有权重信息
        List<SubjectScoreWeightVO> all = subjectScoreWeightService.getAll();

        // 抽取其中的tid组成列表
        List<Integer> tids = new ArrayList<>();

        for (SubjectScoreWeightVO subjectScoreWeightVO : all) {
            tids.add(subjectScoreWeightVO.getTeacher().getTeacherID());
        }

        // 查询未设置权重的老师
        List<Teacher> notInWeight = teacherService.getNotInWeight(tids);
        // 将老师的密码去除
        for (Teacher teacher : notInWeight) {
            teacher.setPassword("");
        }
        result.setCode(600);
        result.setData(notInWeight);
        result.setMsg("查询成功！");
        return JSON.toJSON(result).toString();
    }

    @ResponseBody
    @RequestMapping("/inWeight")
    public String getInWeight() {
        Result result = new Result();

        // 查询所有权重信息
        List<SubjectWeight> all = subjectWeightService.selectAll();

        // 抽取其中的tid组成列表
        List<Integer> tidS = new ArrayList<>();

        for (SubjectWeight subjectWeight : all) {
            tidS.add(subjectWeight.getTid());
        }

        // 查询未设置权重的老师
        List<Teacher> InWeight = teacherService.getInWeight(tidS);
        // 将老师的密码去除
        for (Teacher teacher : InWeight) {
            teacher.setPassword("");
        }
        result.setCode(600);
        result.setData(InWeight);
        result.setMsg("查询成功！");
        return JSON.toJSON(result).toString();
    }

    /**
     * 根据教师ID查询对应的教师信息
     * @param teacherID
     * @return
     */
    @ResponseBody
    @GetMapping("/get")
    public String get(Integer teacherID) {
        Result result = new Result();

        Teacher byTId = teacherService.getByTId(teacherID);
        System.out.println(byTId.getUsername());
        if(byTId != null) {
            // 去除密码
            byTId.setPassword("");
            result.setCode(600);
            result.setData(byTId);
            result.setMsg("查询成功！");
        } else {
            result.setCode(602);
            result.setMsg("教师ID不存在！");
        }

        return JSON.toJSON(result).toString();
    }
}
