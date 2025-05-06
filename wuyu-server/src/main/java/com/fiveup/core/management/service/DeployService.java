package com.fiveup.core.management.service;

import com.fiveup.core.management.manage.entity.TaskInfo;

/**
 * @author 尔宣赫
 * @date 2022/4/27
 */
public interface DeployService {
    TaskInfo startTransaction(String remoteIP, String username, String password);
}
