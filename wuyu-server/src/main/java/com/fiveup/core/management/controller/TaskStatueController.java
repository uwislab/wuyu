package com.fiveup.core.management.controller;

import com.fiveup.core.management.common.CommonResponse;
import com.fiveup.core.management.manage.AsyncTaskManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author 尔宣赫
 * @date 2022/4/27
 */
@Slf4j
@RestController
@CrossOrigin
public class TaskStatueController {

    //注入异步任务管理器
    @Resource
    AsyncTaskManager asyncTaskManager;

    @GetMapping(value = "/api/getTaskStatus")
    public CommonResponse getTaskStatus(@RequestParam("taskId") String taskId) {

        return CommonResponse.ok(asyncTaskManager.getTaskInfo(taskId));
    }
}
