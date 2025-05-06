package com.fiveup.core.management.service.impl;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import com.fiveup.core.management.common.utils.LinuxUtils;
import com.fiveup.core.management.manage.AsyncTaskManager;
import com.fiveup.core.management.manage.entity.BackupResult;
import com.fiveup.core.management.manage.entity.TaskInfo;
import com.fiveup.core.management.mapper.RecoverMapper;
import com.fiveup.core.management.model.Req.BackupReq;
import com.fiveup.core.management.service.BackupRecoverService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.time.LocalDateTime;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author 陈玉庆
 * @date 2024/11/24
 */
@Slf4j
@Service
public class BackupRecoverServiceImpl implements BackupRecoverService {

    /**
     * 注入异步任务管理器
     */
    @Resource
    private AsyncTaskManager asyncTaskManager;

    private static String hostIP = "49.51.69.99";
    private static final String BACKUP_PATH = "D:/wuyu/RecoverData/";
    private static final int MYSQL_PORT = 6603;
    private static final String DB_NAME = "fiveup";
    private static final String SQL_NAME = "fiveup_backup_SQL";
    private static final String ZIP_NAME = "backup.zip"; // 压缩包文件名
    private BackupResult backupResult = new BackupResult();
    private static File file;
    @Value(value = "${spring.datasource.username}")
    private String username;

    @Value(value = "${spring.datasource.password}")
    private String password;

    @Resource
    private RecoverMapper recoverMapper;

    @Override
    public BackupResult startBackup(BackupReq backupReq) {
        // 提交异步任务
        TaskInfo taskInfo = asyncTaskManager.submit(() -> {
            Integer isLocalBackup = backupReq.getLocalBackup();
            if (isLocalBackup.equals(1)) {
                // 本地备份数据库
                file = localBackup();
                backupResult.setBackupFile(file);
            } else if (isLocalBackup.equals(0)) {
                // 远端备份数据库
                localBackup();
                String remoteIP = backupReq.getRemoteIP();
                String remoteUserName = backupReq.getUsername();
                String remotePassword = backupReq.getPassword();
                Connection sshConnection = null;
                try {
                    sshConnection = LinuxUtils.getSSHConnection(remoteIP, 22, remoteUserName, remotePassword);
                    log.info("Linux ssh连接成功");
                    deliverJar(sshConnection, BACKUP_PATH, SQL_NAME + ".sql", BACKUP_PATH);
                    log.info("远程备份完成，备份目录为: " + BACKUP_PATH);
                } catch (IOException e) {
                    log.error("startBackup || error={}", e.getMessage(), e);
                }
            } else {
                log.error("暂无此能力，isLocalBackup=" + isLocalBackup);
            }
        });
        backupResult.setTaskInfo(taskInfo);
        return backupResult;
    }

    public File localBackup() {
        String fileName = BACKUP_PATH + SQL_NAME + ".sql";
        String ZipFileName = BACKUP_PATH + ZIP_NAME;
        // 默认使用linux
        //String cmdPrefix = "/bin/sh -c ";
        String c1 = "/bin/sh";
        String c2 = "-c";
        String os_name = System.getProperty("os.name");

        // 判断是否是windows系统
        if (os_name.toLowerCase().startsWith("win")) {
            //cmdPrefix = "cmd /c ";
            c1 = "cmd";
            c2 = "/c";
        }
        String cmd = "mysqldump"  // mysqldump 命令行命令名
                +" -h" + hostIP
                + " -u" + this.username  // 数据库用户名
                + " -p" + this.password  // 数据库密码
                + " -P" + MYSQL_PORT      // 数据库端口号
                + " " + DB_NAME      // 数据库名
                + " > " + fileName;                   // 最终写入的文件路径
        try {
            System.out.println("第一个参数 " + c1);
            System.out.println("第二个参数 " + c2);
            System.out.println("具体命令 " + cmd);

            log.info("数据库备份START" + LocalDateTime.now());

            /**
             * exec重载方法有一个参数的，window下执行正常，linux下无法完成备份
             *
             * 使用多参数重载方法都可以正常备份
             */
            Process process = Runtime.getRuntime().exec(new String[]{c1, c2, cmd});
            int afterExecutingCode = process.waitFor();
            log.info("execute code : " + afterExecutingCode);
            log.info("数据库备份END" + LocalDateTime.now());
            zipFile(fileName, ZipFileName);
            return new File(ZipFileName);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("数据库备份失败:{}", e.getMessage());
        }
        return null;
    }

    private void zipFile(String sourceFileName, String zipFileName) {
        try (FileOutputStream fos = new FileOutputStream(zipFileName);
             ZipOutputStream zos = new ZipOutputStream(fos);
             FileInputStream fis = new FileInputStream(sourceFileName);
             BufferedInputStream bis = new BufferedInputStream(fis)) {

            ZipEntry zipEntry = new ZipEntry(new File(sourceFileName).getName());
            zos.putNextEntry(zipEntry);
            byte[] bytes = new byte[1024];
            int length;
            while ((length = bis.read(bytes)) >= 0) {
                zos.write(bytes, 0, length);
            }
            zos.closeEntry();
        } catch (IOException e) {
            log.error("文件压缩失败：" + e.getMessage(), e);
        }
    }

    public void deliverJar(Connection connection, String jarDir, String jarName, String targetDir) throws IOException {
        String jarPath = jarDir + jarName;
        SCPClient scpClient = connection.createSCPClient();
        scpClient.put(jarPath, targetDir);
    }

    @Override
    public TaskInfo recoverDB() {
        TaskInfo taskInfo = asyncTaskManager.submit(() -> {
            // recoverMapper.executeRecoverSQL(SQL_NAME + ".sql");
            executeRecoverCMD();
        });
        return taskInfo;
    }

    public void executeRecoverCMD() {
        String fileName = BACKUP_PATH + SQL_NAME + ".sql";

        // 默认使用linux
        //String cmdPrefix = "/bin/sh -c ";
        String c1 = "/bin/sh";
        String c2 = "-c";
        String os_name = System.getProperty("os.name");

        // 判断是否是windows系统
        if (os_name.toLowerCase().startsWith("win")) {
            //cmdPrefix = "cmd /c ";
            c1 = "cmd";
            c2 = "/c";
        }
        String cmd = "mysql"  // mysqldump 命令行命令名
                + " -u" + this.username  // 数据库用户名
                + " -p" + this.password  // 数据库密码
                // + " -P" + MYSQL_PORT      // 数据库端口号
                + " " + DB_NAME      // 数据库名
                + " < " + fileName;                   // 最终写入的文件路径
        try {
            System.out.println("第一个参数 " + c1);
            System.out.println("第二个参数 " + c2);
            System.out.println("具体命令 " + cmd);

            log.info("数据库恢复START" + LocalDateTime.now());

            /**
             * exec重载方法有一个参数的，window下执行正常，linux下无法完成备份
             *
             * 使用多参数重载方法都可以正常备份
             */
            Process process = Runtime.getRuntime().exec(new String[]{c1, c2, cmd});
            int afterExecutingCode = process.waitFor();
            log.info("execute code : " + afterExecutingCode);
            log.info("数据库恢复END" + LocalDateTime.now());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("数据库恢复失败:{}", e.getMessage());
        }
    }
}
