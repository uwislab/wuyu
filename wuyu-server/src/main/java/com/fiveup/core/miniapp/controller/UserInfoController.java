package com.fiveup.core.miniapp.controller;

import com.fiveup.core.miniapp.model.ActivityFamily;
import com.fiveup.core.miniapp.model.UserMini;
import com.fiveup.core.miniapp.service.ClassInfoService;
import com.fiveup.core.miniapp.service.StuInfoService;
import com.fiveup.core.miniapp.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.fiveup.core.management.common.CommonResponse;

import java.util.List;


/**
 * @author shilin
 * @ClassName: ParentController
 * @Date: 2022/9/18 16:24
 */
@RestController
@RequestMapping("/miniapp")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    StuInfoService stuInfoService;

    @Autowired
    private ClassInfoService classInfoService;

    @PostMapping(value = "/login")
    public CommonResponse login(@RequestBody UserMini userMini) {

        UserMini response = userInfoService.login(userMini);
        if (response == null) {
            return CommonResponse.fail(1001, "登录失败");
        } else {
            return CommonResponse.ok(response, "登录成功");
        }
    }

    @PostMapping(value = "/parentInfo")
    public CommonResponse getParent(@RequestBody UserMini userMini) {

        UserMini userMini1 = userInfoService.getParent(userMini);
        if (userMini1 == null) {
            return CommonResponse.fail(1001, "未查询家长信息");
        } else
            return CommonResponse.ok(userMini1);
    }

    @PostMapping(value = "/updateParentInfo")
    public CommonResponse updateParent(@RequestBody UserMini userMini) {

        int count = userInfoService.updateParent(userMini);
        if (count == 1) {
            return CommonResponse.ok(userMini);
        } else
            return CommonResponse.fail(1001, "更新失败");
    }

    @PostMapping(value = "/register")
    public CommonResponse register(@RequestBody UserMini userMini) {
        int classId = classInfoService.getClassIdByStudentNum(Integer.parseInt(userMini.getStudentNum()));
        Long studentClassId=new Long(classId);
        userMini.setClassId(studentClassId);
        int count = userInfoService.addParent(userMini);
        if (count == 0) {
            return CommonResponse.fail(1001, "注册失败");
        } else {
            return CommonResponse.ok(userMini);
        }
    }

    //根据学生姓名绑定学生信息
    @PutMapping("/bindStudent")
    public CommonResponse bindStudent(@RequestBody UserMini usermini,
                                      @RequestParam String studentName){
        String stuNum=stuInfoService.getStuNumByStudentName(studentName);
        UserMini userMini = userInfoService.getParent(usermini);
        Long id= usermini.getId();
        int count=userInfoService.bindStudent(stuNum,id);
        if(count==0){
            return CommonResponse.fail(1001,"绑定失败");
        }
        else{
            return CommonResponse.ok();
        }
    }


}
