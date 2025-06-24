package com.fiveup.core.config;

import com.fiveup.core.questionnaire.interceptor.AuthenticationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 钟承远
 * @date 2022/4/10
 */
@Configuration
public class FiveupWebMVCConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/activityFile/**").addResourceLocations("file:///"+"Users/erxuanhe/IdeaProjects/wuyu-server-zcy/");
        registry.addResourceHandler("/managementFile/**").addResourceLocations("file:///"+"Users/erxuanhe/IdeaProjects/wuyu-server/");
        registry.addResourceHandler("/cultivationFile/**").addResourceLocations("file:///"+"Users/erxuanhe/IdeaProjects/wuyu-server-chw/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthenticationInterceptor()).addPathPatterns("/**");
    }
}
