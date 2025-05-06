package com.fiveup.core.performanceevaluation.controller;

import com.alibaba.fastjson.JSON;
import com.fiveup.core.performanceevaluation.bean.SubjectWeight;
import com.fiveup.core.performanceevaluation.bean.Teacher;
import com.fiveup.core.performanceevaluation.service.SubjectWeightService;
import com.fiveup.core.performanceevaluation.service.TeacherService;
import com.fiveup.core.performanceevaluation.utils.Result;
import com.fiveup.core.performanceevaluation.vo.SubjectWeightVO;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@CrossOrigin(origins ="http://localhost:9210",allowCredentials = "true")
@RequestMapping("/scoreWeight")
public class SubjectWeightController {

    @Autowired
    private SubjectWeightService subjectWeightService;

    @Autowired
    private TeacherService teacherService;

    @ResponseBody
    @PostMapping("/getByTid")
    public String getAll(Integer tid ,HttpSession session, HttpServletResponse response){

//        Object teacher = session.getAttribute("teacher");
//        System.out.println("session = " + session);
        List<SubjectWeightVO> list = new ArrayList<>();
        Result result = new Result();
//        response.setHeader("Set-Cookie", "JSESSIONID=xxx;SameSite=None;Secure");
//        System.out.println(teacher);
//        if("admin".equals(teacher.getUsername())){
//            result.setCode(601);
//        }else{
//            result.setCode(600);
//        }
//        List<SubjectWeight> list1 = subjectWeightService.selectInTid(tidS);
        List<SubjectWeight> list1 = subjectWeightService.selectByTid(tid);



        for(SubjectWeight sub : list1){
            SubjectWeightVO subWei = new SubjectWeightVO();
            if(sub.getParentId() != 0) {
                subWei.setName(subjectWeightService.selectOne(sub.getParentId()).getName());
                subWei.setWeight(subjectWeightService.selectOne(sub.getParentId()).getWeight());
                subWei.setSecondName(sub.getName());
                subWei.setSecondWeight(sub.getWeight());
                subWei.setContent(sub.getContent());
                subWei.setTid(sub.getTid());
                list.add(subWei);
            }
        }
        result.setCode(600);
        result.setData(list);
        result.setMsg("查询成功！");
        return JSON.toJSON(result).toString();
    }
}
