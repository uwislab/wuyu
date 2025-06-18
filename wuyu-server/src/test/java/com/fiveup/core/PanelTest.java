package com.fiveup.core;


import com.fiveup.core.management.controller.PanelController;
import com.fiveup.core.noticeBooklet.domain.NoticeBooklet;
import com.fiveup.core.noticeBooklet.domain.vo.StudentVO;
import com.fiveup.core.noticeBooklet.service.CommentGenerationService;
import com.fiveup.core.noticeBooklet.service.NoticeBookletService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PanelTest {
    private static final Logger logger = LoggerFactory.getLogger(PanelTest.class);
    @Autowired
    private PanelController controller;
    @Autowired
    private NoticeBookletService noticeBookletService;

    // 测试获取通知册内容
    @Test
    public void testCalc() {
        // 测试学号
        List<NoticeBooklet> noticeBooklet1 = noticeBookletService.getNoticeBooklet(2018083062, null, null, true, null);
        // 测试班级
        List<NoticeBooklet> noticeBooklet2 = noticeBookletService.getNoticeBooklet(null, 1, 1, true, null);
        // 测试年级
        List<NoticeBooklet> noticeBooklet3 = noticeBookletService.getNoticeBooklet(null, null, 6, false, null);
        System.out.println(noticeBooklet1);
        System.out.println(noticeBooklet2);
        System.out.println(noticeBooklet3);
    }

    @Test
    void test() {
        controller.getPanelData(1);
    }
}
