package com.fiveup.core;


import com.fiveup.core.management.controller.PanelController;
import com.fiveup.core.noticeBooklet.domain.NoticeBooklet;
import com.fiveup.core.noticeBooklet.service.NoticeBookletService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PanelTest {
    @Autowired
    private PanelController controller;
    @Autowired
    private NoticeBookletService noticeBookletService;

    @Test
    public void testCalc() {
        // 测试学号
        List<NoticeBooklet> noticeBooklet1 = noticeBookletService.getNoticeBooklet(2017083062, null, null);
        // 测试班级
        List<NoticeBooklet> noticeBooklet2 = noticeBookletService.getNoticeBooklet(null, 1, null);
        // 测试年级
        List<NoticeBooklet> noticeBooklet3 = noticeBookletService.getNoticeBooklet(null, null, 6);
        System.out.println(noticeBooklet1);
        System.out.println(noticeBooklet2);
        System.out.println(noticeBooklet3);

    }

    @Test
    void test() {
        controller.getPanelData(1);
    }
}
