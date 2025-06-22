package com.fiveup.core.studentManager.controller;

<<<<<<< HEAD
import com.fiveup.core.commentgeneration.utils.Result;
import com.fiveup.core.studentManager.entity.StudentManager;
import com.fiveup.core.studentManager.pojo.PageBean;
import com.fiveup.core.studentManager.pojo.StudentManagerQuery;
=======
import com.fiveup.core.fuScale.develop_09.common.R;
import com.fiveup.core.management.model.School;
import com.fiveup.core.studentManager.pojo.*;
import com.fiveup.core.studentManager.entity.StudentManager;
import com.fiveup.core.studentManager.pojo.Result;
>>>>>>> bce9104829d01d0a3e3e5a9af9940f0ecd6fcf4c
import com.fiveup.core.studentManager.service.StudentManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("StudentManager")
public class StudentManagerController {
    @Autowired
    private StudentManagerService studentManagerService;

    /**
<<<<<<< HEAD
     * 增加学生
     * @param studentManager
     * @return
     */
    @PostMapping("addStudent")
    public Result<Void> addStudent(@RequestBody StudentManager studentManager){
        studentManagerService.addStudent(studentManager);
        return Result.SUCCESS;
    }

    /**
=======
>>>>>>> bce9104829d01d0a3e3e5a9af9940f0ecd6fcf4c
     * 条件分页查询学生
     * @param studentManagerQuery
     * @return
     */
    @PostMapping("getStudent")
    public Result<PageBean<StudentManager>> getStudent(@RequestBody StudentManagerQuery studentManagerQuery){
        return new Result<>(studentManagerService.getStudent(studentManagerQuery));
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
     * @param studentInsertDTO
     * @return
     */
    @PostMapping("/updateStudent")
    public Result<Void> updateStudent(@RequestBody StudentInsertDTO studentInsertDTO){
        studentManagerService.updateStudent(studentInsertDTO);
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
}
