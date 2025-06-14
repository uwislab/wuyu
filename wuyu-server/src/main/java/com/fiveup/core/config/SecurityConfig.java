package com.fiveup.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
   @author plh
*  @过滤器
 * @date 2025-06-09
 * @ Version 1.0
*/
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors() // 启用CORS支持
                .and()
                .csrf().disable() // 前后端分离通常禁用CSRF
                .authorizeRequests()
                .anyRequest().permitAll();
    }
}
