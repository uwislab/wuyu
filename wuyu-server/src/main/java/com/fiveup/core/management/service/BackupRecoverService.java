package com.fiveup.core.management.service;

import com.fiveup.core.management.manage.entity.BackupResult;
import com.fiveup.core.management.manage.entity.TaskInfo;
import com.fiveup.core.management.model.Req.BackupReq;

/**
 * @author 陈玉庆
 * @date 2024/11/24
 */
public interface BackupRecoverService {
    BackupResult startBackup(BackupReq backupReq);
    TaskInfo recoverDB();
}
