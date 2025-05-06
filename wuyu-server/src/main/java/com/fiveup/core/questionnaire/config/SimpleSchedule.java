package com.fiveup.core.questionnaire.config;

import com.fiveup.core.questionnaire.enums.PaperStatus;
import com.fiveup.core.questionnaire.mapper.PaperMapper;
import com.fiveup.core.questionnaire.mapper.QPaperMapper;
import com.fiveup.core.questionnaire.po.Paper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SimpleSchedule {
@Autowired
private PaperMapper paperMapper;
    @Scheduled(cron = "0 0/1 * * * ?")   //定时器定义，设置执行时间
    private void process() {
//        log.info("定时器开始执行试卷扫描-------start");
        List<Paper> papers = paperMapper.selectTimeOut();
        for (int i = 0; i <papers.size() ; i++) {
             paperMapper.changeStatus(PaperStatus.STOP.getCode(),papers.get(i).getId());
        }
//        log.info("定时器执行试卷扫描结束-------end");

    }
}
