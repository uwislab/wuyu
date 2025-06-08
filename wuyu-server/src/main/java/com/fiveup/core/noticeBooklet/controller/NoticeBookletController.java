package com.fiveup.core.noticeBooklet.controller;

import com.fiveup.core.noticeBooklet.domain.NoticeBooklet;
import com.fiveup.core.noticeBooklet.service.NoticeBookletService;
import com.fiveup.core.management.common.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 通知册相关接口
 *
 * @author huangxingcan
 * @date 2025/6/7 16:38
 */
@RestController
@CrossOrigin
@RequestMapping("/noticeBooklet")
public class NoticeBookletController {

    @Autowired
    private NoticeBookletService noticeBookletService;

    /**
     * 获取通知册内容
     *
     * @param studentNum 学号
     * @param classId    班级id
     * @param gradeId    年级id
     * @param schoolId   学校id
     * @return 通知册列表
     */
    @GetMapping("/get")
    public CommonResponse<List<NoticeBooklet>> getNoticeBooklet(
            @RequestParam(required = false) Long studentNum,
            @RequestParam Integer classId,
            @RequestParam Integer gradeId,
            @RequestParam Long schoolId
    ) {
        List<NoticeBooklet> noticeBookletList = noticeBookletService.getNoticeBooklet(studentNum, classId, gradeId, schoolId);
        return CommonResponse.ok(noticeBookletList);
    }
}