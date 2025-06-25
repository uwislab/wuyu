package com.fiveup.core.util;

import cn.hutool.extra.spring.SpringUtil;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.function.Function;

/**
 * @author 云上起了雾 --> 个人博客: lackofcsy.cn
 * @date 2025/06/20 16:06
 **/
@RequiredArgsConstructor
public class CaffeineUtil {
    private static final Cache<String, Object> cache = SpringUtil.getBean("caffeineCache");

    /**
     * 获取缓存
     * @param key
     * @return
     * @param <T>
     */
    public static <T> Optional<T> get(String key) {
        Object o = cache.getIfPresent(key);
        if (o == null) {
            return Optional.empty();
        }
        return Optional.of((T)o);
    }

    /**
     * 添加缓存
     * @param key
     * @param value
     */
    public static void put(String key, Object value) {
        cache.put(key, value);
    }

    /**
     * 删除缓存
     * @param key
     */
    public static void remove(String key) {
        cache.invalidate(key);
    }

    /**
     * 获取缓存，如果缓存不存在则调用function获取数据并添加缓存
     * @param key
     * @param function
     * @return
     * @param <T>
     */
    public static <T> T get(String key, Function<String, ?> function) {
        return (T) cache.get(key,function);
    }
}
