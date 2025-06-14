// NoticeBookletWordController.java
package com.fiveup.core.noticeBooklet.controller;

import com.fiveup.core.management.common.enums.BizErrorCodeEnum;
import com.fiveup.core.noticeBooklet.domain.NoticeBooklet;
import com.fiveup.core.noticeBooklet.service.NoticeBookletService;
import com.fiveup.core.noticeBooklet.service.NoticeBookletWordService;
import com.fiveup.core.management.common.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/noticeBooklet/word")
public class NoticeBookletWordController {

    @Autowired
    private NoticeBookletService noticeBookletService;

    @Autowired
    private NoticeBookletWordService noticeBookletWordService;

    /**
     * 根据学生信息生成Word文件
     * @param studentId 学号
     * @param classId 班级id
     * @param gradeId 年级id
     * @return 响应结果
     */
    @GetMapping("/generate")
    public CommonResponse<String> generateWord(@RequestParam(required = false) Integer studentId, @RequestParam(required = false) Integer classId, @RequestParam(required = false) Integer gradeId) {
        try {
            // 查询通知册内容
            List<NoticeBooklet> noticeBooklets = noticeBookletService.getNoticeBooklet(studentId, classId, gradeId,false);
            if (noticeBooklets != null && !noticeBooklets.isEmpty()) {
                for (NoticeBooklet noticeBooklet : noticeBooklets) {
                    // 生成Word文件
                    noticeBookletWordService.generateWord(noticeBooklet);
                }
                return CommonResponse.ok("Word文件生成成功");
            } else {
                return CommonResponse.fail(BizErrorCodeEnum.valueOf("未查询到通知册内容"));
            }
        } catch (IOException e) {
            return CommonResponse.fail(BizErrorCodeEnum.valueOf("Word文件生成失败：" + e.getMessage()));
        }
    }
}