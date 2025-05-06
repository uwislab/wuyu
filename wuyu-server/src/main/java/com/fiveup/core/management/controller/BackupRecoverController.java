package com.fiveup.core.management.controller;

import com.fiveup.core.management.common.CommonResponse;
import com.fiveup.core.management.common.enums.BackendErrorCodeEnum;
import com.fiveup.core.management.manage.entity.BackupResult;
import com.fiveup.core.management.manage.entity.TaskInfo;
import com.fiveup.core.management.model.Req.BackupReq;
import com.fiveup.core.management.service.BackupRecoverService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author 陈玉庆
 * @date 2024/11/24
 */
@Slf4j
@RestController
@CrossOrigin // 允许跨域请求
public class BackupRecoverController {

    @Resource // 注入BackupRecoverService服务
    private BackupRecoverService backupRecoverService;

    @PostMapping("/api/startBackup") // 处理POST请求，用于启动备份
    public CommonResponse<BackupResult> backupStart(@RequestBody BackupReq backupReq) {
        try {
            // TODO 检验参数
            BackupResult taskInfo = backupRecoverService.startBackup(backupReq); // 调用服务层的备份方法
            // TaskInfo info = taskInfo.getTaskInfo(); // 获取任务信息
            return CommonResponse.ok(taskInfo); // 返回成功响应
        } catch (Exception e) {
            return CommonResponse.fail(BackendErrorCodeEnum.UNSPECIFIED); // 返回失败响应
        }
    }

    @GetMapping("/api/recoverDataBase") // 处理GET请求，用于恢复数据库
    public CommonResponse<TaskInfo> recoverLocalDataBase() {
        try {
            TaskInfo taskInfo = backupRecoverService.recoverDB(); // 调用服务层的恢复方法
            return CommonResponse.ok(taskInfo); // 返回成功响应
        } catch (Exception e) {
            return CommonResponse.fail(BackendErrorCodeEnum.UNSPECIFIED); // 返回失败响应
        }
    }
}
