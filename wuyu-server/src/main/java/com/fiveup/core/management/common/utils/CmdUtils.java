package com.fiveup.core.management.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author 尔宣赫
 * @date 2022/4/28
 */
public class CmdUtils {
    private static final ExecutorService executorService = Executors.newFixedThreadPool(6);

    public static Map<String, String> execCmd(String cmd) throws IOException, InterruptedException, ExecutionException {
        List<String> cmds = new ArrayList<>();
        cmds.add("bash");
        cmds.add("-c");
        cmds.add(cmd);
        ProcessBuilder pb = new ProcessBuilder(cmds);
        Process process = pb.start();
        Future<String> futureOut = executorService.submit(new ReaTask(process.getInputStream()));
        Future<String> futureError = executorService.submit(new ReaTask(process.getErrorStream()));
        process.waitFor();
        Map<String, String> result = new HashMap<>();
        result.put("error", futureError.get());
        result.put("out", futureOut.get());
        return result;
    }
}

class ReaTask implements Callable<String> {
    InputStream is;

    ReaTask(InputStream is) {
        this.is = is;
    }

    @Override
    public String call() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }
        br.close();
        return sb.toString();
    }
}