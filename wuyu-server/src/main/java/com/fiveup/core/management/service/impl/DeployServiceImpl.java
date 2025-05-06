package com.fiveup.core.management.service.impl;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;
import com.fiveup.core.management.common.utils.LinuxUtils;
import com.fiveup.core.management.manage.AsyncTaskManager;
import com.fiveup.core.management.manage.entity.TaskInfo;
import com.fiveup.core.management.service.DeployService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author 陈玉庆
 * @date 2024/11/25
 */
@Slf4j
@Service
public class DeployServiceImpl implements DeployService {



//    @Resource(name = "asyncServiceExecutor")
//    private Executor executor;\


    /**
     * 注入异步任务管理器
     */
    @Resource
    AsyncTaskManager asyncTaskManager;

    @Override
    public TaskInfo startTransaction(String remoteIP, String username, String password) {
        // 调用任务管理器中的submit去提交一个异步任务
        TaskInfo taskInfo = asyncTaskManager.submit(() -> {
            Connection sshConnection = null;
            try {
                sshConnection = LinuxUtils.getSSHConnection(remoteIP, 22, username, password);
                log.info("Linux ssh连接成功");
                String jarDir = "/devops/coding/jar/";
                String jarName = "fiveup-core-0.0.1-SNAPSHOT.jar";

                String deployDir = "/home/";
                this.deliverJar(sshConnection, jarDir, jarName, deployDir);
                log.info("Jar包securely copy to remote成功");
                this.deploy(sshConnection, deployDir, jarName);
                log.info("Jar包开始deploy");
            } catch (IOException e) {
                log.error("startTransaction || error={}", e.getMessage(), e);
            } finally {
                if (sshConnection != null) {
                    sshConnection.close();
                }
            }
        });
        return taskInfo;
    }

    public void deliverJar(Connection connection, String jarDir, String jarName, String targetDir) throws IOException {


        String jarPath = jarDir + jarName;

        SCPClient scpClient = connection.createSCPClient();
        scpClient.put(jarPath, targetDir);
    }


    public void deploy(Connection connection, String deployDir, String jarName) throws IOException {
        Session session = connection.openSession();
        String cmd = "nohup java -jar " + deployDir + jarName + " &";
        // 执行cmd命令
        session.execCommand(cmd);
        if (session != null) {
            session.close();
        }
    }
}
