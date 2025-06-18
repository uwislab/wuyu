package com.fiveup.core.noticeBooklet.service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ExportZipService {

    /**
     * 生成多个Word文件并打包为ZIP
     * @param response HTTP响应对象
     * @param classId 班级
     * @param gradeId 年级
     */
    public void exportMultipleWords(HttpServletResponse response, Integer classId, Integer gradeId);
}
