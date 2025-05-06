package com.fiveup.core.management.manage.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author 尔宣赫
 * @date 2022/4/27
 */
@Slf4j
@Component
public class AsyncTaskExecutor {

    @Async
    public void executor(AsyncTaskConstructor asyncTaskGenerator, String taskInfo) {
        log.info("AsyncTaskExecutor is executing async task:{}", taskInfo);
        asyncTaskGenerator.async();
    }
}
