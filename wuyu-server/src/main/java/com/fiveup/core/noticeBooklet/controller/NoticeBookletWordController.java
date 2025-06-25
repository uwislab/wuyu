// NoticeBookletWordController.java
package com.fiveup.core.noticeBooklet.controller;

import cn.hutool.http.HttpResponse;
import com.fiveup.core.common.exception.ApiException;
import com.fiveup.core.common.exception.GlobalExceptionHandler;
import com.fiveup.core.management.common.enums.BizErrorCodeEnum;
import com.fiveup.core.noticeBooklet.domain.NoticeBooklet;
import com.fiveup.core.noticeBooklet.service.NoticeBookletService;
import com.fiveup.core.noticeBooklet.service.NoticeBookletWordService;
import com.fiveup.core.management.common.CommonResponse;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/noticeBooklet/word")
public class NoticeBookletWordController {

    @Autowired
    private NoticeBookletService noticeBookletService;

    @Autowired
    private NoticeBookletWordService noticeBookletWordService;

    /**
     * 根据学生ID生成单个Word文件
     * @param studentId 学号
     * @return 响应结果
     */
    @GetMapping("/generate")
    public void generateWord(@RequestParam Integer studentId, HttpServletResponse response){
        try {
            // 查询通知册内容
            List<NoticeBooklet> noticeBooklets = noticeBookletService.getNoticeBooklet(studentId, null, null,true, null);
            if (noticeBooklets != null && !noticeBooklets.isEmpty()) {
                    // 生成Word文件
                    noticeBookletWordService.generateWord(noticeBooklets.get(0),response);
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/generateHTML")
    public CommonResponse<String> generateHTML(@RequestParam Integer studentId) throws IOException {
            // 查询通知册内容
            List<NoticeBooklet> noticeBooklets = noticeBookletService.getNoticeBooklet(studentId, null, null,true, null);
            if(noticeBooklets.isEmpty()) {
                return CommonResponse.fail(500, "未找到通知册内容");
            }
            return CommonResponse.ok(noticeBookletWordService.generateWordToHTML(noticeBooklets.get(0)));
    }

}
