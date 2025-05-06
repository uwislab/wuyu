package com.fiveup.core.performanceevaluation.controller;

import com.fiveup.core.fuScale.develop_09.common.R;
import com.fiveup.core.performanceevaluation.bean.StudentGrades;
import com.fiveup.core.performanceevaluation.service.StudentGradesService;
import com.fiveup.core.performanceevaluation.utils.Result;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/studentGrades")
public class StudentGradesController {

    @Autowired
    private StudentGradesService studentGradesService;


    @GetMapping("/all")
    public Result getStudentGradesByPage(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        PageInfo<StudentGrades> pageInfo = studentGradesService.getStudentGradesByPage(pageNum, pageSize);
        if(!pageInfo.getList().isEmpty()){
            return new Result(200,"成功",pageInfo);
        }
        return new Result(500,"失败",null);
    }

    @GetMapping("/search")
    public Result getStudentGrades(@RequestParam("pageNum") int pageNum,
                                   @RequestParam("pageSize") int pageSize,
                                   @RequestParam("studentNum") String studentNum,
                                   @RequestParam("studentName") String studentName,
                                   @RequestParam("className") String className,
                                   @RequestParam("grade") String grade) {
        PageInfo<StudentGrades> pageInfo = studentGradesService.getStudentGrades(pageNum, pageSize, studentNum,
                studentName, className, grade);
        if(!pageInfo.getList().isEmpty()){
            return new Result(200,"成功",pageInfo);
        }
        return new Result(500,"失败",null);
    }

    @GetMapping("/class")
    public Result getAllClass() {
        if(studentGradesService.getAllClass() != null){
            return new Result(200,"成功",studentGradesService.getAllClass());
        }
        return new Result(500,"失败",null);
    }

    @GetMapping("/grades")
    public Result getAllGrades() {
        if(studentGradesService.getAllGrades() != null){
            return new Result(200,"成功",studentGradesService.getAllGrades());
        }
        return new Result(500,"失败",null);
    }

    @GetMapping("/chart")
    public Result getChartData(@RequestParam("studentNum") String studentNum,
                               @RequestParam("studentName") String studentName,
                               @RequestParam("className") String className,
                               @RequestParam("grade") String grade) {
        if(studentGradesService.getChartData(studentNum, studentName, className, grade) != null){
            return new Result(200,"成功",studentGradesService.getChartData(studentNum, studentName, className, grade));
        }
        return new Result(500,"失败",null);
    }
}
