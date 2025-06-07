package com.fiveup.core.noticeBooklet.controller;

import com.fiveup.core.noticeBooklet.utils.FileUtils;
import com.fiveup.core.noticeBooklet.utils.WordUtils;
import com.fiveup.core.noticeBooklet.utils.ZipUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/noticeBooklet")
public class NoticeBookletController {

    /**
     * 生成多个Word文件并打包为ZIP
     * @param response HTTP响应对象
     * @param ids 多个通知册的id集合
     */
    @GetMapping("/export_multiple_words")
    public void exportMultipleWords(HttpServletResponse response, @RequestParam List<Long> ids) {
        try {
            String path = this.getClass().getClassLoader().getResource("application.yml").getPath();
            String wordPath = FileUtils.getPath(path, FileUtils.WORD_PATH);
            String zipPath = FileUtils.getPath(path, FileUtils.ZIP_PATH);

            // 生成唯一的ZIP文件名
            String zipFileName = "multiple_words_" + System.currentTimeMillis();

            // 存储所有生成的Word文件路径
            List<String> generatedWordPaths = new ArrayList<>();
            // TODO: 根据ids集合查询通知册数据集合

            // TODO: 要处理的Word文件列表，通知册数据集合转成wordFiles集合
            List<Map<String, Object>> wordFiles = new ArrayList<>();
            // 处理每个Word文件
            for (Map<String, Object> wordData : wordFiles) {
                //TODO: 确保包含必要的字段, 记得根据通知册字段改造word文件模板
                if (!wordData.containsKey("title") || !wordData.containsKey("content") || !wordData.containsKey("name")) {
                    continue;
                }

                // 添加日期信息
                wordData.put("year", DateFormatUtils.format(new Date(), "yyyy"));
                wordData.put("month", DateFormatUtils.format(new Date(), "MM"));
                wordData.put("day", DateFormatUtils.format(new Date(), "dd"));

                // 生成Word文件
                String fileName = wordData.get("name").toString() + "_" + System.currentTimeMillis() + ".docx";
                String fullPath = wordPath + "/" + fileName;
                WordUtils.saveWord(wordData, fullPath);
                generatedWordPaths.add(fullPath);
            }

            // 将所有Word文件打包成ZIP
            if (!generatedWordPaths.isEmpty()) {
                ZipUtils.saveMultipleFilesToZip(generatedWordPaths, zipPath, zipFileName, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}
