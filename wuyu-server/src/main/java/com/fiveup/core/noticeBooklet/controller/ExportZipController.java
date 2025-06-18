package com.fiveup.core.noticeBooklet.controller;

import com.fiveup.core.noticeBooklet.service.ExportZipService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/export")
@RequiredArgsConstructor
public class ExportZipController {

    // 注入服务
    private final ExportZipService exportZipService;


    /**
     * 生成多个Word文件并打包为ZIP
     * @param response HTTP响应对象
     * @param classId 班级id
     * @param gradeId 年级 id
     */
    @GetMapping("/zip")
    public void exportMultipleWords(HttpServletResponse response,
                                    @RequestParam(required = false) Integer classId,
                                    @RequestParam(required = false) Integer gradeId)
    {
        exportZipService.exportMultipleWords(response, classId,  gradeId);
    }
}
