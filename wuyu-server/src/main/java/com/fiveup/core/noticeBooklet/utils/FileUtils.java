package com.fiveup.core.noticeBooklet.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @program byh-service-referral
 * @description: 文件工具类
 * @author: chenmet
 * @create: 2019/01/10 10:07
 */
public class FileUtils {


    public static final String WORD_PATH="word/";

    public static final String ZIP_PATH="zip/";

    public static final String SERVICE_NAME="wuyu-server";


    /**
     * 获取文件存放地址
     * @param path
     * @return
     */
    public static String getPath(String path,String name){
        int length=path.indexOf(SERVICE_NAME);
        String newPath=path.substring(0,length)+name;
        File file = new File(newPath);
        //根据文件夹路径创建文件夹
        if(!file.exists()){
            file.mkdirs();
        }
        return newPath;
    }

    /**
     * 下载zip包
     * @param file
     * @param response
     * @return
     */
    public static HttpServletResponse downloadZip(File file, HttpServletResponse response) {
        try {
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(file.getPath()));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            //如果输出的是中文名的文件，在此处就要用URLEncoder.encode方法进行处理
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return response;
    }


    /**
     * 删除目录下所有文件
     * @param path
     * @return
     */
    public static boolean deleteDir(String path){
        File file = new File(path);
        //判断是否待删除目录是否存在
        if(!file.exists()){
            System.out.print("The dir are not exists!");
            return false;
        }
        String[] content = file.list();//取得当前目录下所有文件和文件夹
        for(String name : content){
            File temp = new File(path, name);
            //判断是否是目录
            if(temp.isDirectory()){
                deleteDir(temp.getAbsolutePath());//递归调用，删除目录里的内容
                temp.delete();//删除空目录
            }else{
                if(!temp.delete()){//直接删除文件
                    System.out.print("The dir are not exists!");
                }
            }
        }
        return true;
    }



}
