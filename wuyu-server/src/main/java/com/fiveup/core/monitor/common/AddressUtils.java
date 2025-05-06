package com.fiveup.core.monitor.common;

import cn.hutool.json.JSONException;
import cn.hutool.json.JSONObject;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AddressUtils {

    public String getInnetIp() throws SocketException {
        String localip = null;// 本地IP，如果没有配置外网IP则返回它
        String netip = null;// 外网IP
        Enumeration<NetworkInterface> netInterfaces;
        netInterfaces = NetworkInterface.getNetworkInterfaces();
        InetAddress ip = null;
        boolean finded = false;// 是否找到外网IP
        while (netInterfaces.hasMoreElements() && !finded) {
            NetworkInterface ni = netInterfaces.nextElement();
            Enumeration<InetAddress> address = ni.getInetAddresses();
            while (address.hasMoreElements()) {
                ip = address.nextElement();
                if (!ip.isSiteLocalAddress()
                        &&!ip.isLoopbackAddress()
                        &&ip.getHostAddress().indexOf(":") == -1){// 外网IP
                    netip = ip.getHostAddress();
                    finded = true;
                    break;
                } else if (ip.isSiteLocalAddress()
                        &&!ip.isLoopbackAddress()
                        &&ip.getHostAddress().indexOf(":") == -1){// 内网IP
                    localip = ip.getHostAddress();
                }
            }
        }
        if (netip != null && !"".equals(netip)) {
            return netip;
        } else {
            return localip;
        }
    }

    public String getV4IP() {
        String ip = "";
        String chinaz = "http://ip.tool.chinaz.com/";

        StringBuilder inputLine = new StringBuilder();
        String read = "";
        URL url = null;
        HttpURLConnection urlConnection = null;
        BufferedReader in = null;
        try {
            url = new URL(chinaz);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            while ((read = in.readLine()) != null) {
                inputLine.append(read + "\r\n");
            }
            //System.out.println(inputLine.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Pattern p = Pattern.compile("\\<dd class\\=\"fz24\">(.*?)\\<\\/dd>");
        Matcher m = p.matcher(inputLine.toString());
        if (m.find()) {
            String ipstr = m.group(1);
            ip = ipstr;
            //System.out.println(ipstr);
        }
        return ip;
    }

    public String getAddresses(String content, String encoding) throws UnsupportedEncodingException {
        //设置访问地址
        String urlStr = "http://ip.tool.chinaz.com/service/getIpInfo.php";
        // 从http://whois.pconline.com.cn取得IP所在的省市区信息
        System.out.println(content);
        System.out.println(encoding);
        System.out.println("dddddddddddddddddddddddddddddd");
        String returnStr = this.getResult(urlStr, content, encoding);
        if (returnStr != null) {
            // 处理返回的省市区信息
            // System.out.println(returnStr);
            System.out.println(returnStr);
            String[] temp = returnStr.split(",");
            if (temp.length < 3) {
                return "0";// 无效IP，局域网测试
            }

            String country = ""; //国家
            String area = ""; //地区
            String region = ""; //省份
            String city = ""; //市区
            String county = ""; //地区
            String isp = ""; //ISP公司


            System.out.println();
            for (int i = 0; i < temp.length; i++) {
                switch (i) {
                    case 2:
                        country = (temp[i].split(":"))[1].replaceAll("\"", "");
                        country = URLDecoder.decode(country, encoding);// 国家
                        break;
                    case 3:
                        area = (temp[i].split(":"))[1].replaceAll("\"", "");
                        area = URLDecoder.decode(area, encoding);// 地区
                        break;
                    case 4:
                        region = (temp[i].split(":"))[1].replaceAll("\"", "");
                        region = URLDecoder.decode(region, encoding);// 省份
                        break;
                    case 5:
                        city = (temp[i].split(":"))[1].replaceAll("\"", "");
                        city = URLDecoder.decode(city, encoding);// 市区
                        if ("内网IP".equals(city)) {
                            return "地址为：内网IP";
                        }
                        break;
                    case 6:
                        county = (temp[i].split(":"))[1].replaceAll("\"", "");
                        county = URLDecoder.decode(county, encoding);// 地区
                        break;
                    case 7:
                        isp = (temp[i].split(":"))[1].replaceAll("\"", "");
                        isp = URLDecoder.decode(isp, encoding); // ISP公司
                        break;
                }
            }
            return new StringBuffer("地址为：" + country + ",").append(region + "省,").append(city + "市,").append(county + ",").append("ISP公司：" + isp)
                    .toString();
        }
        return null;
    }

    private String getResult(String urlStr, String content, String encoding) {
        URL url = null;
        HttpURLConnection connection = null;
        try {
            url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();// 新建连接实例
            connection.setConnectTimeout(2000);// 设置连接超时时间，单位毫秒
            connection.setReadTimeout(33000);// 设置读取数据超时时间，单位毫秒
            connection.setDoOutput(true);// 是否打开输出流 true|false
            connection.setDoInput(true);// 是否打开输入流true|false
            connection.setRequestMethod("POST");// 提交方法POST|GET
            connection.setUseCaches(false);// 是否缓存true|false
            connection.connect();// 打开连接端口
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());// 打开输出流往对端服务器写数据
            out.writeBytes(content);// 写数据,也就是提交你的表单 name=xxx&pwd=xxx
            out.flush();// 刷新
            out.close();// 关闭输出流
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), encoding));
            // 往对端写完数据对端服务器返回数据
            // ,以BufferedReader流来读取
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            reader.close();
            return buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();// 关闭连接
            }
        }
        return null;
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }


    private static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    public static String baiduGetCityCode() throws JSONException, IOException{

        String ip="";
        //百度ak，自己去申请就可以
        String ak="";
        //这里调用百度的ip定位api服务 详见 http://api.map.baidu.com/lbsapi/cloud/ip-location-api.htm
        JSONObject json = readJsonFromUrl("http://api.map.baidu.com/location/ip?ip="+ip+"&ak="+ak+"&coor=bd09ll");
        Object obj = ((JSONObject) json.get("content")).get("address_detail");
        String s=obj.toString();
        JSONObject j = new JSONObject(s);
        int city_code=(int) j.get("city_code");
        String code=String.valueOf(city_code);
        return code;
    }

    public static String guideGetCityCode(String ips) throws JSONException, IOException{
        String ip=ips;
        //高德key
        String key="e1b4433618b2f4f6ab1df2bbea565f33";
        JSONObject json = readJsonFromUrl("http://restapi.amap.com/v3/ip?ip="+ip+"&key="+key+"");
        //String province = (String) json.get("province");
        // String city = (String) json.get("city");
        System.out.println(json);
        String adcode = (String) json.get("city");
        return adcode;
    }

    public static double[] getll(String ips) throws IOException {

        String data=guideGetCityCode(ips);
        System.out.println(data);
        Address2 a=new Address2();
        String s3="";
        s3=a.getLnglat(data);
        System.out.println(s3);
        double[] myList=new double[2];
        int i=0;
        for(String s:s3.split(",")){
            myList[i]=Double.parseDouble(s);
            i++;
        }

        return myList;
    }

}