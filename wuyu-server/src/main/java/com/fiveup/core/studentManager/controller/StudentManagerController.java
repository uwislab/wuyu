package com.fiveup.core.studentManager.controller;

import com.fiveup.core.studentManager.pojo.Result;
import com.fiveup.core.studentManager.entity.StudentManager;
import com.fiveup.core.studentManager.pojo.PageBean;
import com.fiveup.core.studentManager.pojo.StudentManagerQuery;
import com.fiveup.core.studentManager.pojo.StudentVO;

import com.fiveup.core.studentManager.service.StudentManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/StudentManager")

public class StudentManagerController {
    @Autowired
    private StudentManagerService studentManagerService;

    /**
     * 增加学生
     * @param studentManager
     * @return
     */
    @PostMapping("/addStudent")
    public Result<Void> addStudent(@RequestBody StudentManager studentManager){
        studentManagerService.addStudent(studentManager);
        return Result.SUCCESS;
    }

    /**
     * 条件分页查询学生
     * @param studentManagerQuery
     * @return
     */
    @PostMapping("/getStudent")
    public Result<PageBean<StudentVO>> getStudent(@RequestBody StudentManagerQuery studentManagerQuery){
        PageBean<StudentVO> pageBean = studentManagerService.getStudentPage(studentManagerQuery);

        return new Result(pageBean);

    /**
     * 修改学生
     * @param studentManager
     * @return
     */
    @PostMapping("updateStudent")
    public Result<Void> updateStudent(@RequestBody StudentManager studentManager){
        studentManagerService.updateStudent(studentManager);
        return Result.SUCCESS;
    }

    /**
     * 删除学生（逻辑删除）
     * @return
     */
    @GetMapping("removeStudent")
    public Result<Void> removeStudent(@RequestParam("id") Integer studentId){
        studentManagerService.removeStudent(studentId);
        return Result.SUCCESS;
    }
}
