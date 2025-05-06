package com.fiveup.core.management.common.utils;

import ch.ethz.ssh2.Connection;
import org.python.jline.internal.Log;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * @author 尔宣赫
 * @date 2022/4/27
 */
@Component
public class LinuxUtils {


    public static Connection getSSHConnection(String host, int port, String userName, String password) throws IOException {

        Connection connection = new Connection(host, port);
        connection.connect();
//        boolean re = connection.authenticateWithPublicKey(userName, file, password);
        boolean re = connection.authenticateWithPassword(userName, password);

        if (re) {
            Log.info("linux连成功");
            return connection;
        } else {
            System.out.println("登录连接失败，请检查用户名、密码");
            return null;

        }
    }

}
