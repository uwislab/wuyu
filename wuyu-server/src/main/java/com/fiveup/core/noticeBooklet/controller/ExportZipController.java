package com.fiveup.core.noticeBooklet.controller;

import com.fiveup.core.noticeBooklet.service.ExportZipService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/export")
@RequiredArgsConstructor
public class ExportZipController {
    private final ExportZipService exportZipService;
    /**
     * 生成多个Word文件并打包为ZIP
     * @param response HTTP响应对象
     * @param studentIds 多个通知册的id集合
     */
    @GetMapping("/zip")
    public void exportMultipleWords(HttpServletResponse response, @RequestParam List<Long> studentIds) {
        exportZipService.exportMultipleWords(response, studentIds);
    }
}
