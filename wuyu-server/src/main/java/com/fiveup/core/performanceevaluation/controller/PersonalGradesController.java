package com.fiveup.core.performanceevaluation.controller;

import com.fiveup.core.performanceevaluation.bean.StudentGrades;
import com.fiveup.core.performanceevaluation.mapper.StudentGradesMapper;
import com.fiveup.core.performanceevaluation.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/personal")
public class PersonalGradesController {

    @Autowired
    private StudentGradesMapper studentGradesMapper;

    @GetMapping("/search")
    public Result getPersonalGrades(@RequestParam("studentNum") String studentNum,
                                   @RequestParam("studentName") String studentName) {
        List<StudentGrades> list = studentGradesMapper.getPersonal(studentNum,studentName);
        if(!list.isEmpty()){
            return new Result(200,"成功",list);
        }
        return new Result(500,"失败",null);
    }
}
