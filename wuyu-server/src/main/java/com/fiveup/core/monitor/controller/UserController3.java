package com.fiveup.core.monitor.controller;


import com.fiveup.core.monitor.common.Result;
import com.fiveup.core.monitor.entity.User3;
import com.fiveup.core.monitor.mapper.UserMapper3;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
@RestController
@RequestMapping("/user3")
public class UserController3 {
    @Resource

    UserMapper3 userMapper3;
    @PostMapping("/login")
    public Result<?> login(@RequestBody User3 user3) {
        LinuxStateForShell s=new LinuxStateForShell();
        userMapper3.updateById(user3);
        return Result.success();
    }
}

