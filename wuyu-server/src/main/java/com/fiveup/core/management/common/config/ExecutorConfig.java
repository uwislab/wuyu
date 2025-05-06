package com.fiveup.core.management.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author 尔宣赫
 * @date 2022/4/27
 */
@Slf4j
@Configuration
@EnableAsync
public class ExecutorConfig implements AsyncConfigurer{

//    @Value("${async.executor.thread.core_pool_size}")
//    private int corePoolSize;
//    @Value("${async.executor.thread.max_pool_size}")
//    private int maxPoolSize;
//    @Value("${async.executor.thread.queue_capacity}")
//    private int queueCapacity;
//    @Value("${async.executor.thread.name.prefix}")
//    private String namePrefix;
//
//    @Bean(name = "asyncServiceExecutor")
//    public Executor asyncServiceExecutor() {
//        log.info("start asyncServiceExecutor");
//
//
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        //配置核心线程数
//        executor.setCorePoolSize(5);
//        //配置最大线程数
//        executor.setMaxPoolSize(10);
//        //配置队列大小
//        executor.setQueueCapacity(999);
//        //配置线程池中的线程的名称前缀
//        executor.setThreadNamePrefix("async-service-");
//
//        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
//        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
//        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
//        //执行初始化
//        executor.initialize();
//        return executor;
//    }


    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setQueueCapacity(20);
        taskExecutor.initialize();
        return taskExecutor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return null;
    }


}

