package com.fiveup.core.miniapp.controller;

import com.fiveup.core.fuScore.model.StudentFuScore;
import com.fiveup.core.management.common.CommonResponse;
import com.fiveup.core.miniapp.model.ActivityFamily;
import com.fiveup.core.miniapp.model.TeacherMini;
import com.fiveup.core.miniapp.service.TchInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/miniapp")
public class TchInfoController {

    @Autowired
    private TchInfoService tchInfoService;

    @GetMapping("/getMonitorInfoByTchId")
    public CommonResponse getTchInfoByTchId(@RequestParam("teacherId") Long teacherId) {

        TeacherMini teacherMini;

        if(teacherId == null) {
            return CommonResponse.fail(1001, "服务器获取教师ID失败");
        } else {
            teacherMini = tchInfoService.getMonitorInfoByTchId(teacherId);
            return CommonResponse.ok(teacherMini);
        }
    }

    @GetMapping("/getMonitorInfoByTchName")
    public CommonResponse getTchInfoByTchName(@RequestParam("teacherName") String teacherName) {

        TeacherMini teacherMini;

        if(teacherName == "") {
            return CommonResponse.fail(1001, "服务器获取教师姓名失败");
        } else {
            teacherMini = tchInfoService.getMonitorInfoByTchName(teacherName);
            return CommonResponse.ok(teacherMini);
        }
    }

    //根据班级获取该班级教师信息
    @GetMapping("/getTeacherInfoByCLass")
    public CommonResponse<List<TeacherMini>> getTeacherInfoByCLass(@RequestParam String gradeNum,@RequestParam String classNum) {

        Integer gradeNumInt = Integer.parseInt(gradeNum.isEmpty()? "0" : gradeNum );
        Integer classNumInt = Integer.parseInt(classNum.isEmpty()? "0" : classNum );

        List<TeacherMini> teacherList = tchInfoService.getTeacherInfoByCLass(gradeNumInt, classNumInt);
        return CommonResponse.ok(teacherList);
    }
}
