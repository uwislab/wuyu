package com.fiveup.core.noticeBooklet.utils;

import org.springframework.util.FileCopyUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
            File zipFile=new File(zipPath+name+".zip");
            zos = new ZipOutputStream(new FileOutputStream(zipFile));
            File[] files = new File(wordPath).listFiles();
            byte[] buffer = new byte[1024];
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
            FileUtils.downloadZip(zipFile,response);
            FileUtils.deleteDir(wordPath);
            FileUtils.deleteDir(zipPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将多个文件打包成ZIP并下载
     * @param filePaths 要打包的文件路径列表
     * @param zipPath ZIP文件保存路径
     * @param zipFileName ZIP文件名
     * @param response HTTP响应对象
     */
    public static void saveMultipleFilesToZip(List<String> filePaths, String zipPath, String zipFileName, HttpServletResponse response) {
        try {
            // 创建ZIP文件
            File zipFile = new File(zipPath + "/" + zipFileName + ".zip");
            if (!zipFile.getParentFile().exists()) {
                zipFile.getParentFile().mkdirs();
            }

            // 创建ZIP输出流
            ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));

            // 添加文件到ZIP
            for (String filePath : filePaths) {
                File file = new File(filePath);
                if (file.exists()) {
                    FileInputStream fis = new FileInputStream(file);
                    ZipEntry zipEntry = new ZipEntry(file.getName());
                    zipOut.putNextEntry(zipEntry);

                    byte[] bytes = new byte[1024];
                    int length;
                    while ((length = fis.read(bytes)) >= 0) {
                        zipOut.write(bytes, 0, length);
                    }
                    fis.close();
                }
            }

            zipOut.close();

            // 设置响应头
            response.setContentType("application/zip");
            response.setHeader("Content-Disposition", "attachment; filename=" + zipFileName + ".zip");

            // 将ZIP文件写入响应
            FileInputStream zipInputStream = new FileInputStream(zipFile);
            FileCopyUtils.copy(zipInputStream, response.getOutputStream());
            zipInputStream.close();

            // 删除临时文件
            zipFile.delete();
            for (String filePath : filePaths) {
                new File(filePath).delete();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}