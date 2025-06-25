package com.fiveup.core.teacherworkspace.config;

import com.fiveup.core.teacherworkspace.common.constant.CommonMessage;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;


@Configuration
public class CacheConfig {
    @Bean
    public Cache<String, String> cache() {
        return Caffeine.newBuilder()
                .expireAfter(new Expiry<String, String>() {
                    @Override
                    public long expireAfterCreate(String key, String value, long currentTime) {
                        return safeCache(key);
                    }

                    @Override
                    public long expireAfterUpdate(String key, String value, long currentTime, long currentDuration) {
                        // 更新时刷新过期时间
                        return safeCache(key);
                    }

                    @Override
                    public long expireAfterRead(String key, String value, long currentTime, long currentDuration) {
                        // 读取时不刷新过期时间
                        return currentDuration;
                    }

                    private long safeCache(String key) {
                        if (key.startsWith(CommonMessage.Cache.LESSON_EXCEL_FAIL.getKey())) {
                            // 写入时过期时间偏移, 保证文件存储在热点之后过期
                            return TimeUnit.SECONDS.toNanos(300);
                        } else {
                            // 默认过期时间
                            return TimeUnit.MINUTES.toNanos(60);
                        }
                    }
                }).build();
    }

}