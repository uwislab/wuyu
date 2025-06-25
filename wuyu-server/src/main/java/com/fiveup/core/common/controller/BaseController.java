package com.fiveup.core.common.controller;

import cn.hutool.core.collection.CollUtil;
import com.fiveup.core.classManage.service.ClassManageService;
import com.fiveup.core.cultivation.entity.BasicTeacher;
import com.fiveup.core.mainpage.domain.po.WebUser;
import com.fiveup.core.performanceevaluation.bean.Teacher;
import com.fiveup.core.performanceevaluation.service.TeacherService;
import com.fiveup.core.sixGroup.entity.BasicClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.fiveup.core.mainpage.mapper.MainPageMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 基础控制器
 * 所有Controller的基类，提供获取当前登录用户信息的通用方法
 */
@Slf4j
public abstract class BaseController {

    @Autowired(required = false)
    private MainPageMapper mainPageMapper;
    @Resource
    private ClassManageService classManageService;
    @Resource
    private TeacherService teacherService;


    /**
     * 获取当前登录用户ID
     * @param request HTTP请求对象
     * @return 当前用户ID，如果未登录则返回null
     */
    protected Integer getCurrentUserId(HttpServletRequest request) {
        WebUser user = getCurrentUser(request);
        return user != null ? user.getId() : null;
    }

    protected  List<Integer> getClassId(HttpServletRequest request) {
        WebUser user = getCurrentUser(request);
        String realName = user.getRealName();
       List<BasicTeacher> teacherList = teacherService.getTeacherByName(realName);
       if(teacherList.size()>0){
           BasicTeacher teacher = teacherList.get(0);
        List<BasicClass> list=   classManageService.getClassByTeacherId(teacher.getId());
        if(CollUtil.isNotEmpty( list)){
            List<Integer> classIds = list.stream().map(basicClass -> basicClass.getId()).collect(Collectors.toList());
            return classIds;
        }
       }
        return new ArrayList<>();
    }

    /**
     * 获取当前登录用户名
     * @param request HTTP请求对象
     * @return 当前用户名，如果未登录则返回null
     */
    protected String getCurrentUsername(HttpServletRequest request) {
        WebUser user = getCurrentUser(request);
        return user != null ? user.getUsername() : null;
    }
    protected String getRealName(HttpServletRequest request) {
        WebUser user = getCurrentUser(request);
        return user != null ? user.getRealName() : null;
    }
    /**
     * 获取当前登录用户身份ID
     * @param request HTTP请求对象
     * @return 当前用户身份ID，如果未登录则返回null
     */
    protected Integer getCurrentUserIdentity(HttpServletRequest request) {
        WebUser user = getCurrentUser(request);
        return user != null ? user.getIdentity() : null;
    }

    /**
     * 获取当前登录用户信息
     * @param request HTTP请求对象
     * @return 当前用户信息，如果未登录则返回null
     */
    protected WebUser getCurrentUser(HttpServletRequest request) {
        if (request == null) {
            log.warn("请求对象为空，无法获取用户信息");
            return null;
        }
        
        // 打印请求头信息，用于调试
        logRequestInfo(request);
        
        // 1. 首先尝试从会话中获取用户信息
        WebUser userInfo = getUserFromSession(request);
        
        // 2. 如果会话中没有，尝试从请求头中获取用户名并查询数据库
        if (userInfo == null) {
            userInfo = getUserFromHeader(request);
        }
        
        // 3. 如果请求头中没有，尝试从Cookie中获取
        if (userInfo == null) {
            userInfo = getUserFromCookie(request);
        }
        
        // 4. 如果Cookie中没有，尝试从请求参数中获取
        if (userInfo == null) {
            userInfo = getUserFromParameter(request);
        }
        
        // 5. 如果请求参数中没有，尝试从Authorization头部获取token
        if (userInfo == null) {
            userInfo = getUserFromToken(request);
        }
        
        // 记录获取结果
        if (userInfo == null) {
            log.warn("无法获取用户信息，所有方法均失败");
        } else {
            log.info("成功获取用户信息：ID={}, 用户名={}, 身份={}", 
                    userInfo.getId(), userInfo.getUsername(), userInfo.getIdentity());
            
            // 如果获取到了用户信息，确保保存到会话中以便后续使用
            try {
                HttpSession session = request.getSession(true);
                session.setAttribute("userInfo", userInfo);
                log.debug("已将用户信息保存到会话中，会话ID: {}", session.getId());
            } catch (Exception e) {
                log.error("保存用户信息到会话时出错", e);
            }
        }
        
        return userInfo;
    }
    
    /**
     * 从会话中获取用户信息
     */
    private WebUser getUserFromSession(HttpServletRequest request) {
        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                log.warn("用户会话不存在");
                return null;
            }
            
            // 打印所有会话属性，用于调试
            Enumeration<String> attributeNames = session.getAttributeNames();
            log.debug("会话属性列表：");
            while (attributeNames.hasMoreElements()) {
                String name = attributeNames.nextElement();
                log.debug("属性名：{}，值：{}", name, session.getAttribute(name));
            }
            
            WebUser userInfo = (WebUser) session.getAttribute("userInfo");
            if (userInfo == null) {
                log.warn("会话中未找到userInfo属性");
                // 尝试其他可能的属性名
                userInfo = (WebUser) session.getAttribute("user");
                if (userInfo == null) {
                    log.warn("会话中也未找到user属性");
                }
            }
            
            return userInfo;
        } catch (Exception e) {
            log.error("从会话获取用户信息时发生异常", e);
            return null;
        }
    }
    
    /**
     * 从请求头中获取用户信息
     */
    private WebUser getUserFromHeader(HttpServletRequest request) {
        try {
            String username = request.getHeader("X-User-Name");
            String userId = request.getHeader("X-User-ID");
            
            if (username == null && userId == null) {
                log.warn("请求头中未找到用户信息");
                return null;
            }
            
            // 如果有用户名，从数据库查询
            if (username != null && mainPageMapper != null) {
                log.info("从请求头获取到用户名：{}", username);
                LambdaQueryWrapper<WebUser> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(WebUser::getUsername, username);
                WebUser user = mainPageMapper.selectOne(wrapper);
                if (user != null) {
                    log.info("根据用户名从数据库查询到用户：{}", user.getUsername());
                    // 将用户信息保存到会话中，以便后续使用
                    HttpSession session = request.getSession(true);
                    session.setAttribute("userInfo", user);
                }
                return user;
            }
            
            return null;
        } catch (Exception e) {
            log.error("从请求头获取用户信息时发生异常", e);
            return null;
        }
    }
    
    /**
     * 从Cookie中获取用户信息
     */
    private WebUser getUserFromCookie(HttpServletRequest request) {
        try {
            Cookie[] cookies = request.getCookies();
            if (cookies == null) {
                log.warn("请求中没有Cookie");
                return null;
            }
            
            String username = null;
            for (Cookie cookie : cookies) {
                if ("username".equals(cookie.getName())) {
                    username = cookie.getValue();
                    log.info("从Cookie获取到用户名：{}", username);
                    break;
                }
            }
            
            if (username != null && mainPageMapper != null) {
                LambdaQueryWrapper<WebUser> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(WebUser::getUsername, username);
                WebUser user = mainPageMapper.selectOne(wrapper);
                if (user != null) {
                    log.info("根据Cookie中的用户名从数据库查询到用户：{}", user.getUsername());
                    // 将用户信息保存到会话中，以便后续使用
                    HttpSession session = request.getSession(true);
                    session.setAttribute("userInfo", user);
                }
                return user;
            }
            
            return null;
        } catch (Exception e) {
            log.error("从Cookie获取用户信息时发生异常", e);
            return null;
        }
    }
    
    /**
     * 从请求参数中获取用户信息
     */
    private WebUser getUserFromParameter(HttpServletRequest request) {
        try {
            String username = request.getParameter("username");
            String userId = request.getParameter("userId");
            
            if (username == null && userId == null) {
                log.debug("请求参数中未找到用户信息");
                return null;
            }
            
            // 如果有用户名，从数据库查询
            if (username != null && mainPageMapper != null) {
                log.info("从请求参数获取到用户名：{}", username);
                LambdaQueryWrapper<WebUser> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(WebUser::getUsername, username);
                WebUser user = mainPageMapper.selectOne(wrapper);
                if (user != null) {
                    log.info("根据请求参数中的用户名从数据库查询到用户：{}", user.getUsername());
                    // 将用户信息保存到会话中，以便后续使用
                    HttpSession session = request.getSession(true);
                    session.setAttribute("userInfo", user);
                }
                return user;
            }
            
            // 如果有用户ID，从数据库查询
            if (userId != null && mainPageMapper != null) {
                try {
                    int id = Integer.parseInt(userId);
                    log.info("从请求参数获取到用户ID：{}", id);
                    WebUser user = mainPageMapper.selectById(id);
                    if (user != null) {
                        log.info("根据请求参数中的用户ID从数据库查询到用户：{}", user.getUsername());
                        // 将用户信息保存到会话中，以便后续使用
                        HttpSession session = request.getSession(true);
                        session.setAttribute("userInfo", user);
                    }
                    return user;
                } catch (NumberFormatException e) {
                    log.error("用户ID格式错误：{}", userId);
                }
            }
            
            return null;
        } catch (Exception e) {
            log.error("从请求参数获取用户信息时发生异常", e);
            return null;
        }
    }
    
    /**
     * 从Authorization头部获取token并解析用户信息
     */
    private WebUser getUserFromToken(HttpServletRequest request) {
        try {
            String token = request.getHeader("Authorization");
            if (token == null) {
                log.debug("请求头中未找到Authorization");
                return null;
            }
            
            // 如果token是Bearer格式，提取实际token值
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            
            log.info("从请求头获取到token：{}", token);
            
            // 这里应该实现token解析逻辑，从token中提取用户信息
            // 由于我们没有完整的token处理逻辑，这里仅作为示例
            // 实际项目中应该根据token解析出用户名或ID，然后查询数据库
            
            // 示例：假设token就是用户名
            if (mainPageMapper != null) {
                LambdaQueryWrapper<WebUser> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(WebUser::getUsername, token);
                WebUser user = mainPageMapper.selectOne(wrapper);
                if (user != null) {
                    log.info("根据token解析的用户名从数据库查询到用户：{}", user.getUsername());
                    // 将用户信息保存到会话中，以便后续使用
                    HttpSession session = request.getSession(true);
                    session.setAttribute("userInfo", user);
                    return user;
                }
            }
            
            return null;
        } catch (Exception e) {
            log.error("从token获取用户信息时发生异常", e);
            return null;
        }
    }
    
    /**
     * 打印请求信息，用于调试
     */
    private void logRequestInfo(HttpServletRequest request) {
        try {
            log.debug("请求URI: {}", request.getRequestURI());
            log.debug("请求方法: {}", request.getMethod());
            log.debug("请求会话ID: {}", request.getRequestedSessionId());
            log.debug("会话是否有效: {}", request.isRequestedSessionIdValid());
            
            // 打印所有请求头
            Enumeration<String> headerNames = request.getHeaderNames();
            log.debug("请求头信息：");
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                log.debug("{}={}", name, request.getHeader(name));
            }
            
            // 打印所有Cookie
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                log.debug("Cookie信息：");
                for (Cookie cookie : cookies) {
                    log.debug("{}={}", cookie.getName(), cookie.getValue());
                }
            } else {
                log.debug("请求中没有Cookie");
            }
        } catch (Exception e) {
            log.error("打印请求信息时发生异常", e);
        }
    }
    
    /**
     * 判断当前用户是否为指定身份
     * @param request HTTP请求对象
     * @param identity 身份ID
     * @return 如果用户身份匹配则返回true，否则返回false
     */
    protected boolean isUserIdentity(HttpServletRequest request, Integer identity) {
        Integer userIdentity = getCurrentUserIdentity(request);
        return userIdentity != null && userIdentity.equals(identity);
    }
    
    /**
     * 判断当前用户是否为校长（身份ID=0）
     */
    protected boolean isPrincipal(HttpServletRequest request) {
        return isUserIdentity(request, 0);
    }
    
    /**
     * 判断当前用户是否为教务主任（身份ID=1）
     */
    protected boolean isAcademicDirector(HttpServletRequest request) {
        return isUserIdentity(request, 1);
    }
    
    /**
     * 判断当前用户是否为班主任（身份ID=2）
     */
    protected boolean isClassTeacher(HttpServletRequest request) {
        return isUserIdentity(request, 2);
    }
    
    /**
     * 判断当前用户是否为普通教师（身份ID=3）
     */
    protected boolean isTeacher(HttpServletRequest request) {
        return isUserIdentity(request, 3);
    }
} 