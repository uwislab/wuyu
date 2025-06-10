package com.fiveup.core.noticeBooklet.controller;

import com.fiveup.core.noticeBooklet.service.NoticeBookletService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
@RequestMapping("/noticeBooklet")
@RequiredArgsConstructor
public class NoticeBookletController {

    private final NoticeBookletService noticeBookletService;
    /**
     * 生成多个Word文件并打包为ZIP
     * @param response HTTP响应对象
     * @param studentIds 多个通知册的id集合
     */
    @GetMapping("/export_multiple_words")
    public void exportMultipleWords(HttpServletResponse response, @RequestParam List<Long> studentIds) {
        noticeBookletService.exportMultipleWords(response, studentIds);
    }
}
