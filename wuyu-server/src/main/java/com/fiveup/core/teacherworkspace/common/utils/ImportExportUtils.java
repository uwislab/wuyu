package com.fiveup.core.teacherworkspace.common.utils;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.fiveup.core.teacherworkspace.common.annotation.ExcelField;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Excel导出工具类
 *
 * @author xp
 * @version 1.0
 */
@Slf4j
public class ImportExportUtils {
    private ImportExportUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 通用导入
     *
     * @param file  MultipartFile
     * @param clazz 目标类
     * @param <T>   泛型
     * @return List<T> 导入的数据
     * @throws IOException IO异常
     */
    public static <T> List<T> importExcel(MultipartFile file, Class<T> clazz) throws IOException {
        ExcelReader reader = ExcelUtil.getReader(file.getInputStream());
        // 创建表头映射
        for (Field field : clazz.getDeclaredFields()) {
            ExcelField annotation = field.getAnnotation(ExcelField.class);
            if (annotation != null) {
                reader.addHeaderAlias(annotation.value(), field.getName());
                log.info("导入字段：{} -> {}", annotation.value(), field.getName());
            }
        }
        return reader.readAll(clazz);
    }

    /**
     * 通用导出
     *
     * @param data      数据
     * @param clazz     目标类
     * @param response  HttpServletResponse
     * @param <T>       泛型
     * @param isExtends 是否继承父类字段
     * @throws IOException IO异常
     */
    public static <T> void exportExcel(List<T> data, Class<T> clazz, HttpServletResponse response, Boolean isExtends) throws IOException {
        ExcelWriter writer = getExcelWriter(clazz, isExtends);
        writer.write(data, true);
        commonExport(writer, response);
    }

    /**
     * 导出模版
     *
     * @param clazz     目标类
     * @param response  HttpServletResponse
     * @param <T>       泛型
     * @param isExtends 是否继承父类字段
     * @throws IOException IO异常
     */
    public static <T> void exportTemplate(Class<T> clazz, HttpServletResponse response, Boolean isExtends) throws IOException {
        ExcelWriter writer = getExcelWriter(clazz, isExtends);
        // 写入表头
        writer.writeHeadRow(writer.getHeaderAlias().values());
        commonExport(writer, response);
    }

    /**
     * 获取ExcelWriter
     *
     * @param clazz     模版类
     * @param isExtends 是否继承父类字段
     * @param <T>       模版类
     * @return ExcelWriter
     */
    private static <T> ExcelWriter getExcelWriter(Class<T> clazz, Boolean isExtends) {
        ExcelWriter writer = ExcelUtil.getWriter();
        List<Field> sonFieldsList = Arrays.stream(clazz.getDeclaredFields()).collect(Collectors.toList());
        if (isExtends.equals(Boolean.TRUE)) {
            List<Field> superFieldsList = Arrays.stream(clazz.getSuperclass().getDeclaredFields()).collect(Collectors.toList());
            sonFieldsList.addAll(superFieldsList);
        }
        for (Field field : sonFieldsList) {
            ExcelField annotation = field.getAnnotation(ExcelField.class);
            if (annotation != null) {
                writer.addHeaderAlias(field.getName(), annotation.value());
            }
        }
        return writer;
    }

    /**
     * 通用导出
     *
     * @param writer   ExcelWriter
     * @param response HttpServletResponse
     * @throws IOException IO异常
     */
    private static void commonExport(ExcelWriter writer, HttpServletResponse response) throws IOException {
        // 设置响应类型
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        // 设置字符编码
        response.setCharacterEncoding("utf-8");
        // 设置响应头信息
        response.setHeader("Content-disposition",
                "attachment;filename*=utf-8''" + URLEncoder.encode(new Date().getTime() + ".xls", StandardCharsets.UTF_8.toString()));
        try {
            ServletOutputStream out = response.getOutputStream();
            writer.flush(out, true);
            out.close();
        } finally {
            writer.close();
        }
    }

}
