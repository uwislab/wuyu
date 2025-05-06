package com.fiveup.core.fuScale.aop;


import com.fiveup.core.mainpage.domain.po.WebUser;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Date;

@Component
@Aspect
public class SysAspect {

    @Pointcut("@annotation(com.fiveup.core.fuScale.aop.Log)")
    private void pointcut(){
    }


    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 通过工具类获取Request对象
        RequestAttributes reqa = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) reqa;
        HttpServletRequest request = sra.getRequest();



        // 获取被增强类和方法的信息
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        // 获取被增强的方法对象
        Method method = methodSignature.getMethod();
        // 从方法中解析注解
        if (method != null) {
            Log logAnnotation = method.getAnnotation(Log.class);
            if (logAnnotation != null) {
                System.out.println("====================日志记录==================");
                System.out.println("操作名: " + logAnnotation.name());
            }
        }
        // 方法名字
        String name = method.getName();
        System.out.println("方法名: " + name);
        // 访问的url
        String url = request.getRequestURL().toString();
        System.out.println("URL: " + url);
        // 请求方式
        String method1 = request.getMethod();
        System.out.println("请求方式: " + method1);

        // 登录IP
        String ip = request.getRemoteAddr();
        System.out.println("请求ip: " + ip);

        // 操作时间
        System.out.println("操作时间: " + new Date());
        System.out.println("===============================================");

        return joinPoint.proceed();
    }



}
