package com.fiveup.core.monitor.controller;

import com.alibaba.fastjson.JSONObject;
import com.fiveup.core.monitor.common.Datetime;
import com.fiveup.core.monitor.entity.User2;
import com.fiveup.core.monitor.entity.User3;
import com.jcraft.jsch.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class LinuxStateForShell {
    public static final String CPU_MEM_SHELL = "top -b -n 1";
    public static final String FILES_SHELL = "df -hl";
    public static final String ips = "curl ipinfo.io/json";
    public static final String[] COMMANDS = {CPU_MEM_SHELL, FILES_SHELL,ips};
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private static Session session;
    public  String Ipture = "";
    private static boolean connect(String user, String passwd, String host) {
        JSch jsch = new JSch();
        try {
            session = jsch.getSession(user, host, 22);
            session.setPassword(passwd);
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
        } catch (JSchException e) {
            e.printStackTrace();
            System.out.println("connect error !");
            return false;
        }
        return true;
    }
    public static Map<String, String> runDistanceShell(String[] commands, String user, String passwd, String host) {
        try {
            if (!connect(user, passwd, host)) {
                return null;
            }
        }catch (Exception e){
            return null;
        }
        int i=0;
        Map<String, String> map = new HashMap<>();
        StringBuilder stringBuffer;
        BufferedReader reader = null;
        Channel channel = null;
        try {
            for (String command : commands) {
                stringBuffer = new StringBuilder();
                channel = session.openChannel("exec");
                ((ChannelExec) channel).setCommand(command);
                channel.setInputStream(null);
                ((ChannelExec) channel).setErrStream(System.err);
                channel.connect();
                InputStream in = channel.getInputStream();
                reader = new BufferedReader(new InputStreamReader(in));
                String buf;
                while ((buf = reader.readLine()) != null) {
                    if (buf.contains("PID")) {
                        break;
                    }
                    stringBuffer.append(buf.trim()).append(LINE_SEPARATOR);
                }
                if(i<2){       //每个命令存储自己返回数据-用于后续对返回数据进行处理
                    map.put(command, stringBuffer.toString());
                }
                else {
                    String s1=stringBuffer.toString();
                    JSONObject jsonObject = JSONObject.parseObject(s1.replace("\n","").replace("\r",""));
                    map.put("ip",jsonObject.getString("ip"));
                }
                i++;
            }
        } catch (IOException | JSchException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (channel != null) {
                channel.disconnect();
            }
            session.disconnect();
        }
        return map;
    }
    public static Map<String, String> runLocalShell(String[] commands) {
        int i=0;
        Runtime runtime = Runtime.getRuntime();
        Map<String, String> map = new HashMap<>();
        Map<String, String> map2 = new HashMap<>();
        StringBuilder stringBuffer;
        BufferedReader reader;
        Process process;
        for (String command : commands) {
            stringBuffer = new StringBuilder();
            try {
                process = runtime.exec(command);
                InputStream inputStream = process.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String buf;
                while ((buf = reader.readLine()) != null) {
                    if (buf.contains("PID")) {
                        break;
                    }
                    stringBuffer.append(buf.trim()).append(LINE_SEPARATOR);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            if(i<2){
                map.put(command, stringBuffer.toString());
            }
            else {
            }
            i++;
        }
        return map;
    }


    private static List<String> disposeResultMessage(Map<String, String> result) {
        StringBuilder buffer = new StringBuilder();
        List<String> list = new ArrayList<String>();
        for (String command : COMMANDS) {
            String commandResult = result.get(command);
            if (null == commandResult) continue;
            if (command.equals(CPU_MEM_SHELL)) {
                String[] strings = commandResult.split(LINE_SEPARATOR);
                for (String line : strings) {
                    line = line.toUpperCase();
                    if (line.startsWith("%CPU(S):")) {
                        String cpuStr = "";
                        try {
                            cpuStr += line.split(":")[1].split(",")[0].replace("US", "").replace(" ","");
                        } catch (Exception e) {
                            e.printStackTrace();
                            cpuStr += "计算过程出错";
                        }
                        list.add(cpuStr);
                        buffer.append(cpuStr).append(LINE_SEPARATOR);
                    } else if (line.startsWith("KIB MEM")) {
                        String memStr = "";
                        try {
                            memStr += line.split(":")[1]
                                    .replace(" TOTAL,", "s")
                                    .replace(" USED, ", "s")
                                    .replace(" FREE, ", "s")
                                    .replace(" BUFF/CACHE", "s").replace("  ","");
                            for (String s:memStr.split("s")){
                                list.add(s);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            memStr += "计算过程出错";
                            buffer.append(memStr).append(LINE_SEPARATOR);
                            continue;
                        }
                        buffer.append(memStr).append(LINE_SEPARATOR);
                    }
                }
            } else if (command.equals(FILES_SHELL)) {
                buffer.append("系统磁盘状态:");
                try {
                    for(String s:disposeFilesSystem(commandResult).replace("大小","")
                            .replace("已使用","")
                            .replace(",","")
                            .replace("已使用","")
                            .replace("空闲","")
                            .replace("G","")
                            .split(" ")){
                        if (s.equals("")){
                        }
                        else {
                            list.add(s);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    buffer.append("计算过程出错").append(LINE_SEPARATOR);
                }
            }
        }
        return list;
    }

    private static String disposeFilesSystem(String commandResult) {
        String[] strings = commandResult.split(LINE_SEPARATOR);
        int size = 0;
        int used = 0;
        for (int i = 0; i < strings.length ; i++) {
            if (i == 0) continue;
            int temp = 0;
            int turn=0;
            int t=0;
            ArrayList<String> list2 = new ArrayList();
            for (String s : strings[i].split(" ")) {
                t++;
                if(s.equals("")){
                }
                else {
                    list2.add(s);
                }
            }
            t=0;
            for(String s:list2){
                t++;
                if(t==2){
                    size += disposeUnit(s);
                }
                if (t==3){
                    used += disposeUnit2(s);
                }
            }
        }
        return new StringBuilder().append("大小 ").append(size).append("G , 已使用").append(used).append("G ,空闲")
                .append(size - used).append("G").toString();
    }

    private static float disposeUnit2(String s) {
        try {
            int k=0;
            if (s.equals("0")){
                return 0;
            }
            else {
                return disposeUnit(s);
            }
        }catch (NumberFormatException e){
            return 0;
        }
    }
    private static float disposeUnit(String s) {
        try {
            s = s.toUpperCase();
            String lastIndex = s.substring(s.length() - 1);
            String num = s.substring(0, s.length() - 1);
            Float parseFloat = Float.parseFloat(num);
            if (lastIndex.equals("G")) {
                return parseFloat;
            } else if (lastIndex.equals("T")) {
                return parseFloat * 1024;
            } else if (lastIndex.equals("M")) {
                return parseFloat / 1024;
            } else if (lastIndex.equals("K") || lastIndex.equals("KB")) {
                return parseFloat / (1024 * 1024);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
        return 0;
    }
    public static User3 Res(User2 args) {
        User3 user3 = new User3();
        Datetime s1=new Datetime();
        try{
            Map<String, String> result = runDistanceShell(COMMANDS, args.getUsername(), args.getPassword(), args.getIp());
            List<String> buffer=disposeResultMessage(result);
            int i=0;
            for(String s: buffer){
                if (i==0){
                    user3.setCpu(s);
                }
                else if(i==1){
                    user3.setMemtoatal(s);
                }
                else if(i==2){
                    user3.setMemused(s);
                }
                else if(i==3){
                    user3.setMemfree(s);
                }
                else if(i==4){
                    user3.setMemsave(s);
                }
                else if(i==5){
                    user3.setSwaptotal(s);
                }
                else if(i==6){
                    user3.setSwapused(s);
                }
                else if(i==7){
                    user3.setSwapfree(s);
                }
                System.out.println(i);
                i++;
            }
            user3.setDate(s1.getdate());
            user3.setTrueip(result.get("ip"));
            user3.setIp(args.getIp());
        }catch (Exception e){
            return  user3;
        }
        return user3;
    }
}