package com.fiveup.core.miniapp.controller;

import com.fiveup.core.management.common.CommonResponse;
import com.fiveup.core.miniapp.model.ClassMini;
import com.fiveup.core.miniapp.service.ClassInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author shilin
 * @date 2022/9/19
 */

@Slf4j
@RestController
@RequestMapping("/miniapp")
@CrossOrigin
public class ClassInfoController {

    @Resource
    private ClassInfoService classInfoService;

    @GetMapping("/getClassInfo")
    public CommonResponse<ClassMini> getMonitorInfo(@RequestParam("classId") String classId) {
        System.out.println(classId);
        if (classId == "") {
            return CommonResponse.fail(1001, "服务端获取班级ID失败");
        } else {
            ClassMini classMini;
            classMini = classInfoService.getClassInfoByClassId(Long.valueOf(classId));
            return CommonResponse.ok(classMini);
        }
    }

    //通过学号获得学生所在班级
    @GetMapping("/getStudentClass")
    public CommonResponse<ClassMini> getStudentClass(@RequestParam("studentNum") String studentNum) {
        ClassMini classMini = classInfoService.getStudentClass(Long.valueOf(studentNum));
        return CommonResponse.ok(classMini);
    }
}
