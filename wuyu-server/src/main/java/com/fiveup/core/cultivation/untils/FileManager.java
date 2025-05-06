package com.fiveup.core.cultivation.untils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Harvi
 */
public class FileManager {
    private static final int FILE_SIZE_LIMIT = 2 * 1024 * 1024;
    private static final String[] FILE_FORMATS = {"pdf", "doc", "docx"};
//    private static final String DISK = "D:";
//    private static final String ABSOLUTE_PATH = "/upimgs/fiveup/files/";
    private static final String ABSOLUTE_PATH_LINUX = "/Users/erxuanhe/IdeaProjects/wuyu-server-chw/guidances/";

    public static Map<String, String> upload(MultipartFile file) {
        Map<String, String> map = new HashMap<>(10);

        String fileNo = UUID.randomUUID().toString().replace("-", "").substring(1, 11);
        String fileFormat = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf("."));

        // 检查文件大小和文件格式是否符合要求
        boolean canUpload = false;
        System.out.println(file.getSize());
        if (file.getSize() >= FILE_SIZE_LIMIT) {
            for (String format: FILE_FORMATS) {
                if (fileFormat.equals(format)) {
                    canUpload = true;
                    break;
                }
            }
        }

        if (canUpload) {
            return null;
        }

        String newName = fileNo + fileFormat;

        // 创建目录
//        File dir = new File(DISK + ABSOLUTE_PATH);
        File dir = new File(ABSOLUTE_PATH_LINUX);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                return null;
            }
        }

        // 创建文件
        File targetFile = new File(dir, newName);
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String oldName = file.getOriginalFilename().substring(0,file.getOriginalFilename().lastIndexOf("."));

        map.put("fileName", oldName);
//        map.put("fileLocation", ABSOLUTE_PATH + newName);
        map.put("fileLocation", "http://82.157.231.88:9200/cultivationFile/guidances/" + newName);
        return map;
    }
    
     public static boolean delete(String fileName) {
//        File file = new File(DISK + path);
         File file = new File("/Users/erxuanhe/IdeaProjects/wuyu-server-chw/guidances/" + fileName);
        if (file.exists()) {
            return file.delete();
        }
        return false;
     }
}
