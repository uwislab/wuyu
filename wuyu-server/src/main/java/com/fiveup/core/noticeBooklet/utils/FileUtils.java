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

    //文件存放地址
    public static final String WORD_PATH="word/";
    //zip存放地址
    public static final String ZIP_PATH="zip/";
    //服务名称
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
            // 获取文件名
            byte[] buffer = new byte[fis.available()];
            // fis.read(buffer);
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            // contentType
            response.setContentType("application/octet-stream");

            //如果输出的是中文名的文件，在此处就要用URLEncoder.encode方法进行处理
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
            //创建输入流
            toClient.write(buffer);
            //关闭输入流
            toClient.flush();
            //关闭输出流
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
        //取得当前目录下所有文件和文件夹
        String[] content = file.list();
        for(String name : content){
            File temp = new File(path, name);
            //判断是否是目录
            if(temp.isDirectory()){
                //递归调用，删除目录里的内容
                deleteDir(temp.getAbsolutePath());
                //删除空目录
                temp.delete();
            }else{
                //直接删除文件
                if(!temp.delete()){
                    System.out.print("The dir are not exists!");
                }
            }
        }
        return true;
    }
}
