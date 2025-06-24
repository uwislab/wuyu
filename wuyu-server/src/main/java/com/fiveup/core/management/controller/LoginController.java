package com.fiveup.core.management.controller;

import cn.hutool.http.server.HttpServerRequest;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fiveup.core.common.api.CommonResult;
import com.fiveup.core.mainpage.domain.po.WebUser;
import com.fiveup.core.mainpage.mapper.MainPageMapper;
import com.fiveup.core.management.pojo.MainUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/mainLogin")
public class LoginController {

    @Resource
    MainPageMapper mainPageMapper;

    @PostMapping("/login")
    public CommonResult<WebUser> login(@RequestBody MainUser mainUser, HttpSession session) {
        String username = mainUser.getUsername();
        LambdaQueryWrapper<WebUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WebUser::getUsername, username);
        WebUser userByUsername = mainPageMapper.selectOne(wrapper);
        if (userByUsername == null || !userByUsername.getPassword().equals(mainUser.getPassword())) //userByUsername.getIdentity() != mainUser.getIdentityId()
        {
            System.out.println(userByUsername.getIdentity());
            return CommonResult.failed("账号或者密码错误");
        }
        userByUsername.setPassword(null);
        session.setAttribute("userInfo", userByUsername);
        return CommonResult.success(userByUsername);
    }
}
