package com.fiveup.core.management.controller;

import cn.hutool.http.server.HttpServerRequest;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fiveup.core.common.api.CommonResult;
import com.fiveup.core.common.controller.BaseController;
import com.fiveup.core.mainpage.domain.po.WebUser;
import com.fiveup.core.mainpage.mapper.MainPageMapper;
import com.fiveup.core.management.pojo.MainUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/api/mainLogin")
@Slf4j
public class LoginController extends BaseController {

    @Resource
    MainPageMapper mainPageMapper;
    
    // 存储手机号与验证码的映射，实际项目中应该使用Redis等缓存服务
    private final Map<String, String> smsCodeMap = new HashMap<>();

    @PostMapping("/login")
    public CommonResult<WebUser> login(@RequestBody MainUser mainUser, HttpServletRequest request, HttpServletResponse response) {
        String username = mainUser.getUsername();
        log.info("用户登录: {}", username);
        
        LambdaQueryWrapper<WebUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WebUser::getUsername, username);
        WebUser userByUsername = mainPageMapper.selectOne(wrapper);
        
        if (userByUsername == null || !userByUsername.getPassword().equals(mainUser.getPassword())) {
            log.warn("登录失败: 用户名或密码错误");
            return CommonResult.failed("账号或者密码错误");
        }
        
        // 检查用户状态
        Integer status = userByUsername.getStatus();
        if (status != null && status == 1) {
            log.warn("登录失败: 用户[{}]已被禁用", username);
            return CommonResult.failed("账号已被禁用，请联系管理员");
        }
        
        // 密码验证成功，移除密码字段
        userByUsername.setPassword(null);
        
        // 获取会话并保存用户信息
        HttpSession session = request.getSession(true);
        session.setAttribute("userInfo", userByUsername);
        
        // 记录会话ID
        String sessionId = session.getId();
        log.info("用户 {} 登录成功，会话ID: {}", username, sessionId);
        
        // 设置Cookie
        Cookie usernameCookie = new Cookie("username", username);
        usernameCookie.setPath("/");
        usernameCookie.setMaxAge(3600); // 1小时
        response.addCookie(usernameCookie);
        
        // 设置用户ID的Cookie
        Cookie userIdCookie = new Cookie("userId", String.valueOf(userByUsername.getId()));
        userIdCookie.setPath("/");
        userIdCookie.setMaxAge(3600); // 1小时
        response.addCookie(userIdCookie);
        
        // 设置请求头，方便前端获取用户信息
        response.setHeader("X-User-Name", username);
        response.setHeader("X-User-ID", String.valueOf(userByUsername.getId()));
        
        return CommonResult.success(userByUsername);
    }
    
    /**
     * 发送短信验证码
     */
    @PostMapping("/sendSmsCode")
    public CommonResult<HashMap> sendSmsCode(@RequestBody Map<String, String> params) {
        String phoneNumber = params.get("phoneNumber");
        
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return CommonResult.failed("手机号不能为空");
        }
        
        // 验证手机号格式
        if (!phoneNumber.matches("^1[3-9]\\d{9}$")) {
            return CommonResult.failed("手机号格式不正确");
        }
        
        // 查询该手机号是否存在用户
        LambdaQueryWrapper<WebUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WebUser::getPhone, phoneNumber);
        WebUser user = mainPageMapper.selectOne(wrapper);
        
        if (user == null) {
            return CommonResult.failed("该手机号未注册");
        }
        
        // 检查用户状态
        Integer status = user.getStatus();
        if (status != null && status == 1) {
            log.warn("发送验证码失败: 用户[{}]已被禁用", user.getUsername());
            return CommonResult.failed("账号已被禁用，请联系管理员");
        }
        
        // 生成6位随机验证码
        String code = generateRandomCode(6);
        
        // 存储验证码（实际项目中应该使用Redis设置过期时间）
        smsCodeMap.put(phoneNumber, code);
        
        // 模拟发送短信，实际项目中应调用短信服务API
        log.info("向手机号 {} 发送验证码: {}", phoneNumber, code);
        HashMap<String, String> map = new HashMap<>();
        map.put("msg","验证码发送成功");
        map.put("code",code);
        return CommonResult.success(map);
    }
    
    /**
     * 验证码登录
     */
    @PostMapping("/loginBySms")
    public CommonResult<WebUser> loginBySms(@RequestBody Map<String, String> params, HttpServletRequest request, HttpServletResponse response) {
        String phoneNumber = params.get("phoneNumber");
        String code = params.get("code");
        
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return CommonResult.failed("手机号不能为空");
        }
        
        if (code == null || code.isEmpty()) {
            return CommonResult.failed("验证码不能为空");
        }
        
        // 验证码校验
        String storedCode = smsCodeMap.get(phoneNumber);
        if (storedCode == null || !storedCode.equals(code)) {
            return CommonResult.failed("验证码错误或已过期");
        }
        
        // 验证成功后移除验证码
        smsCodeMap.remove(phoneNumber);
        
        // 查询用户信息
        LambdaQueryWrapper<WebUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WebUser::getPhone, phoneNumber);
        WebUser user = mainPageMapper.selectOne(wrapper);
        
        if (user == null) {
            return CommonResult.failed("用户不存在");
        }
        
        // 检查用户状态
        Integer status = user.getStatus();
        if (status != null && status == 1) {
            log.warn("登录失败: 用户[{}]已被禁用", user.getUsername());
            return CommonResult.failed("账号已被禁用，请联系管理员");
        }
        
        // 移除密码字段
        user.setPassword(null);
        
        // 获取会话并保存用户信息
        HttpSession session = request.getSession(true);
        session.setAttribute("userInfo", user);
        
        // 记录会话ID
        String sessionId = session.getId();
        log.info("用户 {} 通过验证码登录成功，会话ID: {}", user.getUsername(), sessionId);
        
        // 设置Cookie
        Cookie usernameCookie = new Cookie("username", user.getUsername());
        usernameCookie.setPath("/");
        usernameCookie.setMaxAge(3600); // 1小时
        response.addCookie(usernameCookie);
        
        // 设置用户ID的Cookie
        Cookie userIdCookie = new Cookie("userId", String.valueOf(user.getId()));
        userIdCookie.setPath("/");
        userIdCookie.setMaxAge(3600); // 1小时
        response.addCookie(userIdCookie);
        
        // 设置请求头
        response.setHeader("X-User-Name", user.getUsername());
        response.setHeader("X-User-ID", String.valueOf(user.getId()));
        
        return CommonResult.success(user);
    }
    
    /**
     * 生成指定长度的随机数字验证码
     */
    private String generateRandomCode(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
    
    /**
     * 获取当前登录用户信息
     */
    @PostMapping("/current")
    public CommonResult<WebUser> getLoginUserInfo(HttpServletRequest request) {
        WebUser user = getCurrentUser(request);
        if (user != null) {
            return CommonResult.success(user);
        } else {
            return CommonResult.failed("未获取到当前登录用户信息");
        }
    }
    
    /**
     * 注销登录
     */
    @PostMapping("/logout")
    public CommonResult<String> logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        
        // 清除Cookie
        Cookie usernameCookie = new Cookie("username", null);
        usernameCookie.setPath("/");
        usernameCookie.setMaxAge(0);
        response.addCookie(usernameCookie);
        
        Cookie userIdCookie = new Cookie("userId", null);
        userIdCookie.setPath("/");
        userIdCookie.setMaxAge(0);
        response.addCookie(userIdCookie);
        
        return CommonResult.success("注销成功");
    }
}
