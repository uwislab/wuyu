package com.fiveup.core.questionnaire.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fiveup.core.questionnaire.annotation.RequireToken;
import com.fiveup.core.questionnaire.dto.User;
import com.fiveup.core.questionnaire.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {

        // 如果不是映射到方法直接通过
        if(!(object instanceof HandlerMethod)){
            return true;
        }

        Method method = ((HandlerMethod) object).getMethod();

        // 检查是否要求JwtToken
        if (method.isAnnotationPresent(RequireToken.class)) {

            // 从 http 请求头中取出 token
            String token = httpServletRequest.getHeader("token");

            // 执行认证
            String userId;
            if (token == null) {
                throw new RuntimeException("无token，请重新登录");
            }

            try {
                userId = JWT.decode(token).getAudience().get(0);
            } catch (JWTDecodeException j) {
                throw new RuntimeException("401");
            }

            User user = userMapper.getUserByName(userId);
            if (user == null) {
                throw new RuntimeException("用户不存在，请重新登录");
            }

            // 验证 token
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
            try {
                jwtVerifier.verify(token);
            } catch (JWTVerificationException e) {
                throw new RuntimeException("401");
            }
        }

        return true;
    }
}
