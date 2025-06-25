package com.fiveup.core.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author 云上起了雾 --> 个人博客: lackofcsy.cn
 * @date 2025/06/20 16:16
 **/
@Configuration
public class CaffeineConfig {
    
    @Bean
    public Cache<String, Object> caffeineCache() {
        return Caffeine.newBuilder()
                .initialCapacity(100)
                .expireAfterWrite(60, TimeUnit.MINUTES)
                .build();
    }
}
