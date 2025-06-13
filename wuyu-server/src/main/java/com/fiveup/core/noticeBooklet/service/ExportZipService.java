package com.fiveup.core.noticeBooklet.service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ExportZipService {

    /**
     * 生成多个Word文件并打包为ZIP
     * @param response HTTP响应对象
     * @param ids 多个通知册的id集合
     */
    public void exportMultipleWords(HttpServletResponse response, List<Long> ids);
}
