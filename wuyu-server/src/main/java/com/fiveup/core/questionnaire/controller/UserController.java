package com.fiveup.core.questionnaire.controller;

import com.fiveup.core.management.common.CommonResponse;
import com.fiveup.core.questionnaire.annotation.RequireToken;
import com.fiveup.core.questionnaire.dto.Identity;
import com.fiveup.core.questionnaire.dto.User;
import com.fiveup.core.questionnaire.service.UserService;
import com.fiveup.core.questionnaire.vo.ResponseVO;
import com.fiveup.core.questionnaire.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName: UserController
 * @Date: 2021/4/26 13:42
 * @author zhaomin
 */
@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseVO addUser(@RequestBody UserVO userVO){
        return userService.addUser(userVO);
    }

    //教师用户登陆
    @PostMapping(value = "/login")
    public CommonResponse login(@RequestBody User user){
        User response = userService.login(user);
        System.out.println("职能为："+ response.getIdentity());
//        System.out.println(response.getUsername());
        if(response==null){
            return CommonResponse.fail(10001,"登录失败，请检查登录信息！");
        }else{
            return CommonResponse.ok(response);
        }
    }

    /* 这里的loginToken是用于需要获得JWT Token情况下的用户登陆API。
     * 原来的login我没有去动，怕兼容性问题。这个项目的登陆简直一团乱麻...
     * 如果某个Method标记有RequireToken的话，那么请考虑使用这个API登陆，并将获得到的String填入HTTP HEADER里面的token一项中，
     * 否则将会被AuthenticationInterceptor拦截。
     */
    @PostMapping(value = "/loginToken")
    public CommonResponse loginToken(@RequestBody User user){
        String response = userService.loginAndSignJwtToken(user);

        if(response == null) {
            System.out.println("Login Failed: " + user.getUsername());
            return CommonResponse.fail(402,"登录失败，请检查登录信息！");
        }else{
            System.out.println("Login Success. Assign JWT Token: " + response);
            return CommonResponse.ok(response);
        }
    }


    //查询所有系统角色
    @PostMapping(value = "/getIdentity")
    public CommonResponse getIdentity(){
        System.out.println("...");
        List<Identity> identityList = userService.getIdentity();
        if(identityList==null){
            return CommonResponse.fail(10001,"查询失败");
        }else{
            return CommonResponse.ok(identityList);
        }
    }

    @RequireToken
    @GetMapping(value = "/validateToken")
    public CommonResponse validateToken()
    {
        return CommonResponse.ok("You are logged in.");
    }

}

