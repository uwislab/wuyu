package com.fiveup.core.noticeBooklet.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @program byh-service-referral
 * @description: zip工具类
 * @author: chenmet
 * @create: 2019/01/09 17:13
 */
public class ZipUtils {

    /**
     * 根据资源文件夹，目标文件夹，文件名打包成zip文件下载
     * @param wordPath
     * @param zipPath
     * @param name
     * @param response
     */
    public static void saveZip(String wordPath, String zipPath, String name, HttpServletResponse response){
        try {
            ZipOutputStream zos = null;
            //创建zip文件
            File zipFile=new File(zipPath+name+".zip");
            zos = new ZipOutputStream(new FileOutputStream(zipFile));
            File[] files = new File(wordPath).listFiles();
            byte[] buffer = new byte[1024];
            //读取文件
            for (File file2 : files) {
                FileInputStream fis = new FileInputStream(file2);
                zos.putNextEntry(new ZipEntry(file2.getName()));
                int len;
                //读入需要下载的文件的内容，打包到zip文件
                while ((len = fis.read(buffer)) != -1) {
                    zos.write(buffer, 0, len);
                }
                zos.flush();
                zos.closeEntry();
                fis.close();
            }
            zos.close();
            //下载zip文件
            FileUtils.downloadZip(zipFile,response);
            //删除word文件目录
            FileUtils.deleteDir(wordPath);
            //删除zip文件目录
            FileUtils.deleteDir(zipPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量打包成zip文件
     * @param wordStreams
     * @return
     * @throws IOException
     */
    public static ByteArrayOutputStream zipWordStreams(List<ByteArrayOutputStream> wordStreams) throws IOException {
        //创建zip文件
        ByteArrayOutputStream zipOut = new ByteArrayOutputStream();
        //创建zip输出流
        ZipOutputStream zos = new ZipOutputStream(zipOut);
        int i = 1;
        //循环写入文件
        for (ByteArrayOutputStream wordStream : wordStreams) {
            //创建zip条目
            ZipEntry entry = new ZipEntry("doc" + i + ".docx");
            //写入文件
            zos.putNextEntry(entry);
            //写入文件内容
            zos.write(wordStream.toByteArray());
            //关闭zip条目
            zos.closeEntry();
            i++;
        }
        zos.close();
        return zipOut;
    }
}