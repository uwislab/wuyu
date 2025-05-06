package com.fiveup.core.management.manage.entity;

import java.io.File;

public class BackupResult {
    private TaskInfo taskInfo;
    private File backupFile;

    public BackupResult(TaskInfo taskInfo, File backupFile) {
        this.taskInfo = taskInfo;
        this.backupFile = backupFile;
    }
    public BackupResult() {}

    public TaskInfo getTaskInfo() {
        return taskInfo;
    }

    public File getBackupFile() {
        return backupFile;
    }

    public void setTaskInfo(TaskInfo taskInfo) {
        this.taskInfo = taskInfo;
    }

    public void setBackupFile(File backupFile) {
        this.backupFile = backupFile;
    }
}
