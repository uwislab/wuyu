package com.fiveup.core.questionnaire.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fiveup.core.common.result.Result;
import com.fiveup.core.questionnaire.entity.Student;
import com.fiveup.core.questionnaire.service.StudentService;
import com.fiveup.core.questionnaire.vo.StudentVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @ApiOperation(value = "获取所有学生信息")
    @GetMapping("findAll")
    public Result findAll(){
        List<Student> list = studentService.list();
        return  Result.ok(list);
    }

    @ApiOperation(value = "条件查询带分页")
    @PostMapping("findPage/{current}/{limit}")
    public Result findPage(@PathVariable long current,
                           @PathVariable long limit,
                           @RequestBody(required = false) StudentVO studentVO){
//        创建 Page对象，传递当前页，每页记录数
        System.out.println(limit);
        System.out.println(current);
        Page<Student> page = new Page<>(current, limit);
//        构建条件
        QueryWrapper<Student> wrapper=new QueryWrapper<>();

        String name = studentVO.getStudentName();//学生姓名
        String id = studentVO.getStudentId();//学生学号
        String grade = studentVO.getStudentGrade();//学生年级
        String classNumber = studentVO.getStudentClassNumber();//学生班级

        if(!StringUtils.isEmpty(grade)){
            wrapper.eq("student_grade", studentVO.getStudentGrade());
        }

        if(!StringUtils.isEmpty(name)){
            wrapper.eq("student_name", studentVO.getStudentName());
        }

        if (!StringUtils.isEmpty(id)){
            wrapper.eq("student_id", studentVO.getStudentId());
        }

        /*查询班级需要查询年级和班级两个条件*/
        if(!StringUtils.isEmpty(classNumber)){
            wrapper.eq("student_class_number", studentVO.getStudentClassNumber()).
                    eq("student_grade",studentVO.getStudentGrade());
        }

        Page<Student> pageStudent = studentService.page(page, wrapper);
        return Result.ok(pageStudent);
    }

}
