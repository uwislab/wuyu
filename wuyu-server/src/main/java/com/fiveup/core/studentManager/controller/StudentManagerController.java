package com.fiveup.core.studentManager.controller;

import com.fiveup.core.common.result.Result;
import com.fiveup.core.studentManager.entity.StudentManager;
import com.fiveup.core.studentManager.pojo.PageBean;
import com.fiveup.core.studentManager.pojo.StudentManagerQuery;
import com.fiveup.core.studentManager.service.StudentManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("StudentManager")
public class StudentManagerController {
    @Autowired
    private StudentManagerService studentManagerService;

    /**
     * 增加学生
     * @param studentManager
     * @return
     */
    @PostMapping("addStudent")
    public Result addStudent(@RequestBody StudentManager studentManager){
        studentManagerService.addStudent(studentManager);
        return Result.ok(null);
    }

    /**
     * 条件分页查询学生
     * @param studentManagerQuery
     * @return
     */
    @PostMapping("getStudent")
    public Result<PageBean<StudentManager>> getStudent(@RequestBody StudentManagerQuery studentManagerQuery){
//        return new Result<>(studentManagerService.getStudent(studentManagerQuery));
        PageBean<StudentManager> pageBean = studentManagerService.getStudentPage(studentManagerQuery);

        return Result.success(pageBean);
    }

    /**
     * 修改学生
     * @param studentManager
     * @return
     */
    @PostMapping("updateStudent")
    public Result updateStudent(@RequestBody StudentManager studentManager){
        studentManagerService.updateStudent(studentManager);
        return Result.ok(null);
    }

    /**
     * 删除学生（逻辑删除）
     * @return
     */
    @GetMapping("removeStudent")
    public Result removeStudent(@RequestParam("id") Integer studentId){
        studentManagerService.removeStudent(studentId);
        return Result.ok(null);
    }
}
