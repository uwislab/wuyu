package com.fiveup.core.noticeBooklet.utils;

import cn.afterturn.easypoi.word.WordExportUtil;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SingleWordUtils {

    // 使用 {{key}} 的正则匹配
    private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\{\\{(.*?)\\}\\}");

    /**
     * 导出Word文档到响应流
     */
    public static void exportWordByResponse(Map<String, Object> param, HttpServletResponse response) throws IOException {
        String templPath = "templates/wordss.docx";

        try (InputStream inputStream = WordExportUtil.class.getClassLoader().getResourceAsStream(templPath);
             XWPFDocument doc = new XWPFDocument(inputStream)) {

            // 替换段落中的变量
            for (org.apache.poi.xwpf.usermodel.XWPFParagraph paragraph : doc.getParagraphs()) {
                replaceInParagraph(paragraph, param);
            }

            // 替换表格中的变量
            for (org.apache.poi.xwpf.usermodel.XWPFTable table : doc.getTables()) {
                for (org.apache.poi.xwpf.usermodel.XWPFTableRow row : table.getRows()) {
                    for (org.apache.poi.xwpf.usermodel.XWPFTableCell cell : row.getTableCells()) {
                        for (org.apache.poi.xwpf.usermodel.XWPFParagraph paragraph : cell.getParagraphs()) {
                            replaceInParagraph(paragraph, param);
                        }
                    }
                }
            }

            // 设置响应头，让浏览器下载文件
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=generated-document.docx");

            // 输出到响应流
            doc.write(response.getOutputStream());
        } catch (IOException e) {
            throw new IOException("导出Word失败: " + templPath, e);
        }
    }

    /**
     * 替换word段落中的占位符 ${key}
     */
    private static void replaceInParagraph(org.apache.poi.xwpf.usermodel.XWPFParagraph paragraph, Map<String, Object> params) {
        String text = paragraph.getText();
        if (text == null || text.isEmpty()) return;

        Matcher matcher = PLACEHOLDER_PATTERN.matcher(text);
        StringBuffer sb = new StringBuffer();

        while (matcher.find()) {
            String key = matcher.group(1).trim();
            Object value = params.getOrDefault(key, "");
            String replacement = value != null ? value.toString() : "";
            matcher.appendReplacement(sb, replacement);
        }
        matcher.appendTail(sb);

        if (!sb.toString().equals(text)) {
            // 删除旧段落内容
            for (int i = paragraph.getRuns().size() - 1; i >= 0; i--) {
                paragraph.removeRun(i);
            }
            // 添加新内容
            paragraph.createRun().setText(sb.toString(), 0);
        }
    }

    /**
     * 根据 HTML 模板生成预览页面并写入响应流
     */
    public static String generateHtmlPreview(Map<String, Object> params) throws IOException {
        String templatePath = "templates/notice_booklet.html";
        // 读取 HTML 模板文件
        InputStream inputStream = SingleWordUtils.class.getClassLoader().getResourceAsStream(templatePath);
        if (inputStream == null) {
            throw new IOException("模板文件不存在: " + templatePath);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        StringBuilder htmlBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            //htmlBuilder.append(line).append("\n");
            htmlBuilder.append(line);
        }
        reader.close();

        // 替换占位符 {{key}}
        return replacePlaceholders(htmlBuilder.toString(), params);
    }

    /**
     * 替换 HTML 中的 {{key}} 占位符
     */
    private static String replacePlaceholders(String html, Map<String, Object> params) {
        Matcher matcher = PLACEHOLDER_PATTERN.matcher(html);
        StringBuffer sb = new StringBuffer();

        while (matcher.find()) {
            String key = matcher.group(1).trim();
            Object value = params.getOrDefault(key, "");
            String replacement = value != null ? value.toString() : "";
            matcher.appendReplacement(sb, replacement);
        }
        matcher.appendTail(sb);

        return sb.toString();
    }
}