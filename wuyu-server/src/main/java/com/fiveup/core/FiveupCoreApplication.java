package com.fiveup.core;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.mybatis.spring.annotation.MapperScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableAspectJAutoProxy(proxyTargetClass=true)
@SpringBootApplication
@EnableScheduling
@EnableSwagger2
@EnableSwaggerBootstrapUI
@MapperScan(basePackages = {"com.fiveup.core.management.mapper", "com.fiveup.core.events.mapper", "com.fiveup.core.demonstrate.mapper","com.fiveup.core.monitor.entity"})

@MapperScan(basePackages = {"com.fiveup.core.fuScale.develop_09.Mapper"})
public class FiveupCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(FiveupCoreApplication.class, args);
    }
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

}
