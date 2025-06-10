package com.fiveup.core;


import com.fiveup.core.management.controller.PanelController;
import com.fiveup.core.noticeBooklet.domain.NoticeBooklet;
import com.fiveup.core.noticeBooklet.service.CommentGenerationService;
import com.fiveup.core.noticeBooklet.service.NoticeBookletService;
import lombok.SneakyThrows;
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

    @Autowired
    private CommentGenerationService commentGenerationService;

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


    @SneakyThrows
    @Test
    public void test1() {
        String studentName  = "刘成";
        Long studentId = 2017083062L;
        String comment = commentGenerationService.generateCommentForStudent(studentName, studentId);
        System.out.println(comment);
    }
}
