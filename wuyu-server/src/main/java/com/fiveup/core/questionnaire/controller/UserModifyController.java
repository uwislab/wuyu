package com.fiveup.core.questionnaire.controller;

import com.fiveup.core.questionnaire.dto.ModifyPwdData;
import com.fiveup.core.questionnaire.dto.ModifyUserInfo;
import com.fiveup.core.questionnaire.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class UserModifyController {
    @Autowired
    private UserService userService;

    @PostMapping("/modifyPassword")
    public Boolean modifyPwd(@RequestBody ModifyPwdData modifyPwdData){
        System.out.println(modifyPwdData.getUsername());
        int i = userService.modifyPwd(modifyPwdData);
        System.out.println(i);
        if (i!=0){
            return true;
        }else{
            return false;
        }
    }

    @PostMapping("modifyUserInfo")
    public Boolean modifyUserInfo(@RequestBody ModifyUserInfo modifyUserInfo){
        int i = userService.modifyInfo(modifyUserInfo);
        if (i!=0){
            return true;
        }else{
            return false;
        }
    }

}
