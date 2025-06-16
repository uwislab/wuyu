package com.fiveup.core.studentManager.controller;

import com.fiveup.core.fuScale.develop_09.common.R;
import com.fiveup.core.management.model.School;
import com.fiveup.core.studentManager.pojo.*;
import com.fiveup.core.studentManager.entity.StudentManager;
import com.fiveup.core.studentManager.pojo.Result;
import com.fiveup.core.studentManager.service.StudentManagerService;
import jnr.ffi.annotations.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/StudentManager")

public class StudentManagerController {
    @Autowired
    private StudentManagerService studentManagerService;

    /**
     * 条件分页查询学生
     * @param studentManagerQuery
     * @return
     */
    @PostMapping("/getStudent")
    public Result<PageBean<StudentVO>> getStudent(@RequestBody StudentManagerQuery studentManagerQuery) {
        PageBean<StudentVO> pageBean = studentManagerService.getStudentPage(studentManagerQuery);

        return new Result(pageBean);
    }

    /**
     * 增加学生
     * @param studentInsertDTO
     * @return
     */
    @PostMapping("/addStudent")
    public Result<Void> addStudent(@RequestBody StudentInsertDTO studentInsertDTO){
        studentManagerService.addStudent(studentInsertDTO);
        return Result.SUCCESS;
    }

    /**
     * 修改学生
     * @param studentManager
     * @return
     */
    @PostMapping("/updateStudent")
    public Result<Void> updateStudent(@RequestBody StudentManager studentManager){
        studentManagerService.updateStudent(studentManager);
        return Result.SUCCESS;
    }

    /**
     * 删除学生（逻辑删除）
     * @return
     */
    @GetMapping("/removeStudent")
    public Result<Void> removeStudent(@RequestParam("id") Integer studentId){
        studentManagerService.removeStudent(studentId);
        return Result.SUCCESS;
    }

    @GetMapping("/getSchool")
    public Result<List<School>> getSchool() {
        List<School> list = studentManagerService.getSchool();

        return new Result(list);
    }

    @GetMapping("/getClassName")
    public Result<List<String>> getClassName() {
        List<String> list = studentManagerService.getClassName();

        return new Result(list);
    }

    @GetMapping("/getGrade")
    public Result<List<Integer>> getGrade() {
        List<Integer> list = studentManagerService.getGrade();

        return new Result(list);
    }
}
