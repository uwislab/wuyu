package com.fiveup.core.questionnaire.config;


import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.fiveup.core.questionnaire.mapper")
public class HospConfig {

    /*
    * 分页插件
    * */
    @Bean
    public PaginationInnerInterceptor paginationInterceptor(){
        return  new PaginationInnerInterceptor();
    }
}
