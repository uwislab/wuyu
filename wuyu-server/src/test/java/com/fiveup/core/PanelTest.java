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
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@SpringBootTest
public class PanelTest {
    private static final Logger logger = LoggerFactory.getLogger(PanelTest.class);
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
        String studentName = "张三";
        Long studentId = 2018083062L;
        try {
            String comment = commentGenerationService.generateCommentForStudent(studentName, studentId);
            System.out.println(comment);
        } catch (Exception e) {
            logger.error("生成评语时出错，学生姓名: {}, 学生ID: {}", studentName, studentId, e);
            System.out.println("生成评语时出错，请查看日志获取详细信息。");
        }
    }
}
