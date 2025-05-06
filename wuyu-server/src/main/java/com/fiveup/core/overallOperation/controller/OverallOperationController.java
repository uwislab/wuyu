package com.fiveup.core.overallOperation.controller;

import com.fiveup.core.common.result.Result;
import com.fiveup.core.overallOperation.domain.*;
import com.fiveup.core.overallOperation.domain.StudentScore;
import com.fiveup.core.overallOperation.service.OverallOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="/overallOperation")
public class OverallOperationController {

    @Autowired
    OverallOperationService overallOperationService;
    @RequestMapping(value="/classInfo")
    public Result getClassInfo(Integer grade,Integer term){
        if(grade==null){
            grade=1;
        }
        if(term==null){
            term=0;
        }
        List<FuVo> list = overallOperationService.getClassInfo(grade,term);
        return Result.ok(list);
    }
    @RequestMapping(value="/studentRatio")
    public Result getStudentRatio(Integer grade,Integer term){
        if(grade==null){
            grade=1;
        }
        if(term==null){
            term=0;
        }
        List<StudentRatio> list = overallOperationService.getStudentRation(grade,term);
        return Result.ok(list);
    }

    @RequestMapping(value="/changeYear")
    public Result getTopThreeByYear(@RequestParam(required=false) String year){
        if(year==null){
            year="202012";
        }
        List<List<List<FuClassScore>>> topThree = overallOperationService.getTopThreeByYear(year);
        return  Result.ok(topThree);
    }

    @RequestMapping(value="/pie")
    public Result Pie(){
        List<Pie> pie = new ArrayList<>();
        pie = overallOperationService.pie();
        return Result.ok(pie);
    }

    //学生成绩信息
    @RequestMapping(value="/studentScore")
    public Result getStudentScore(String stuGrade, String stuClass, String stuTerm){
        //年级
        int a = 0;
        if(stuGrade==null){
            stuGrade="1";
            a = a +1;
        }
        //班级
        if(stuClass==null){
            stuClass="1";
            a = a +1;
        }
        //学期
        if(stuTerm==null){
            stuTerm="202012";
            a = a +1;
//            stuTerm="202012";
        }
        if(a > 0){
            System.out.println(a);
        }
        //封装查询学生信息
        List<StudentScore> list = overallOperationService.getStudentScore(stuGrade,stuClass,stuTerm);
        return Result.ok(list);
    }

    //学生录入情况
    @RequestMapping(value="/studentEnter")
    public Result getStudentEnter(String stuGrade, String stuClass, String stuTerm){
        if(stuGrade==null){
            stuGrade="1";
        }
        if(stuClass==null){
            stuClass="1";
        }
        if(stuTerm==null){
            stuTerm="202012";
        }
        //封装录入信息
        StudentRatio studentRatio =overallOperationService.getClassStudentEnter(stuGrade,stuClass,stuTerm);;
        return Result.ok(studentRatio);
    }
    @PostMapping("/studentUpdate")
    public Result updateStu(@RequestBody FuStudentDto stu){
        System.out.println(stu);
        return Result.ok(overallOperationService.updateStu(stu));
    }
}
