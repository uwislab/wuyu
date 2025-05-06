package com.fiveup.core.management.controller;

import com.alibaba.excel.util.StringUtils;
import com.fiveup.core.management.common.CommonResponse;
import com.fiveup.core.management.common.enums.BackendErrorCodeEnum;
import com.fiveup.core.management.manage.entity.TaskInfo;
import com.fiveup.core.management.model.Req.DeployReq;
import com.fiveup.core.management.service.DeployService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author 陈玉庆
 * @date 2024/11/25
 */
@Slf4j
@RestController
@RequestMapping("/api/deploy")
@CrossOrigin
public class DeployController {


    @Resource
    private DeployService deployService;

    @PostMapping("/start")
    public CommonResponse<TaskInfo> startToDeploy(@RequestBody DeployReq deployReq) {

        String remoteIP = deployReq.getRemoteIP();
        String username  = deployReq.getUsername();
        String password  = deployReq.getPassword();
        if(StringUtils.isEmpty(remoteIP) || StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return CommonResponse.fail(BackendErrorCodeEnum.PARAMS_VALIDATION_ERRNO);
        }
        TaskInfo taskInfo = deployService.startTransaction(remoteIP, username, password);
        return CommonResponse.ok(taskInfo);
    }
}
