package com.fiveup.core.monitor.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Address2 {
    public static String getLnglat(String address) {
        String geturl = "http://restapi.amap.com/v3/geocode/geo?key="+"e1b4433618b2f4f6ab1df2bbea565f33"+"&address="+address;
        String location = "";
        try {
            URL url = new URL(geturl);    // 把字符串转换为URL请求地址
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();// 打开连接
            connection.connect();// 连接会话
            // 获取输入流
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();// 关闭流
            connection.disconnect();
            JSONObject a = JSON.parseObject(sb.toString());
            //判断输入的位置点是否存在
            System.out.println(sb.toString());
            if(a.getJSONArray("geocodes").size()>0)
                location=a.getJSONArray("geocodes").getJSONObject(0).get("location").toString();
            System.out.println(location);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("失败!");
        }
        return location;
    }

}
