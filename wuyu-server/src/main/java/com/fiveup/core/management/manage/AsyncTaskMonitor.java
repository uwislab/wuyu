package com.fiveup.core.management.manage;

import com.fiveup.core.management.manage.entity.TaskInfo;
import com.fiveup.core.management.manage.manageenum.TaskStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author 尔宣赫
 * @date 2022/4/27
 */
@Slf4j
@Component
@Aspect
public class AsyncTaskMonitor {

    @Autowired
    AsyncTaskManager manager;

    @Around("execution(* com.fiveup.core.management.manage.service.AsyncTaskExecutor.*(..))")
    public void taskHandle(ProceedingJoinPoint pjp) {
        //获取taskId
        String taskId = pjp.getArgs()[1].toString();

        //获取任务信息
        TaskInfo taskInfo = manager.getTaskInfo(taskId);
        log.info("AsyncTaskMonitor is monitoring async task:{}", taskId);
        taskInfo.setStatus(TaskStatusEnum.RUNNING);
        manager.setTaskInfo(taskInfo);
        TaskStatusEnum status = null;

        try {
            pjp.proceed();
            status = TaskStatusEnum.SUCCESS;
        } catch (Throwable throwable) {
            status = TaskStatusEnum.FAILED;
            log.error("AsyncTaskMonitor:async task {} is failed.Error info:{}", taskId, throwable.getMessage());
        }

        taskInfo.setEndTime(new Date());
        taskInfo.setStatus(status);
        taskInfo.setTotalTime();
        manager.setTaskInfo(taskInfo);
    }

}
