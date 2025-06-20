package com.fiveup.core.noticeBooklet.service.impl;

import com.fiveup.core.noticeBooklet.domain.NoticeBooklet;
import com.fiveup.core.noticeBooklet.service.NoticeBookletWordService;
import com.fiveup.core.noticeBooklet.utils.SingleWordUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class NoticeBookletWordServiceImpl implements NoticeBookletWordService {

    @Override
    public void generateWord(NoticeBooklet noticeBooklet, HttpServletResponse response) throws IOException {
        Map<String, Object> wordData = new HashMap<>();
        wordData.put("studentName", noticeBooklet.getStudentName());
        wordData.put("studentClassNumber", noticeBooklet.getStudentClassNumber());
        wordData.put("studentGrade", noticeBooklet.getStudentGrade());
        wordData.put("sDeyu", noticeBooklet.getSDeyu() == null ? "0" : noticeBooklet.getSDeyu().toString());
        wordData.put("sZhiyu", noticeBooklet.getSZhiyu() == null ? "0" : noticeBooklet.getSZhiyu().toString());
        wordData.put("sTiyu", noticeBooklet.getSTiyu() == null ? "0" : noticeBooklet.getSTiyu().toString());
        wordData.put("sMeiyu", noticeBooklet.getSMeiyu() == null ? "0" : noticeBooklet.getSMeiyu().toString());
        wordData.put("sLaoyu", noticeBooklet.getSLaoyu() == null ? "0" : noticeBooklet.getSLaoyu().toString());
        wordData.put("pDeyu", noticeBooklet.getPDeyu() == null ? "0" : noticeBooklet.getPDeyu().toString());
        wordData.put("pZhiyu", noticeBooklet.getPZhiyu() == null ? "0" : noticeBooklet.getPZhiyu().toString());
        wordData.put("pTiyu", noticeBooklet.getPTiyu() == null ? "0" : noticeBooklet.getPTiyu().toString());
        wordData.put("pMeiyu", noticeBooklet.getPMeiyu() == null ? "0" : noticeBooklet.getPMeiyu().toString());
        wordData.put("pLaoyu", noticeBooklet.getPLaoyu() == null ? "0" : noticeBooklet.getPLaoyu().toString());
        wordData.put("pPlan", noticeBooklet.getPPlan() == null ? "无" : noticeBooklet.getPPlan());
        wordData.put("comment", noticeBooklet.getComment() == null ? "无" : noticeBooklet.getComment());
        wordData.put("remark", noticeBooklet.getRemark() == null ? "无" : noticeBooklet.getRemark());

        // 获取当前日期
        LocalDate currentDate = LocalDate.now();
        wordData.put("year", currentDate.getYear());
        wordData.put("month", currentDate.getMonthValue());
        wordData.put("day", currentDate.getDayOfMonth());


        // 生成Word文件
        SingleWordUtils.exportWordByResponse(wordData, response);
    }

    @Override
    public String generateWordToHTML(NoticeBooklet noticeBooklet) throws IOException {
        Map<String, Object> wordData = new HashMap<>();
        wordData.put("studentName", noticeBooklet.getStudentName());
        wordData.put("studentClassNumber", noticeBooklet.getStudentClassNumber());
        wordData.put("studentGrade", noticeBooklet.getStudentGrade());
        wordData.put("sDeyu", noticeBooklet.getSDeyu() == null ? "0" : noticeBooklet.getSDeyu().toString());
        wordData.put("sZhiyu", noticeBooklet.getSZhiyu() == null ? "0" : noticeBooklet.getSZhiyu().toString());
        wordData.put("sTiyu", noticeBooklet.getSTiyu() == null ? "0" : noticeBooklet.getSTiyu().toString());
        wordData.put("sMeiyu", noticeBooklet.getSMeiyu() == null ? "0" : noticeBooklet.getSMeiyu().toString());
        wordData.put("sLaoyu", noticeBooklet.getSLaoyu() == null ? "0" : noticeBooklet.getSLaoyu().toString());
        wordData.put("pDeyu", noticeBooklet.getPDeyu() == null ? "0" : noticeBooklet.getPDeyu().toString());
        wordData.put("pZhiyu", noticeBooklet.getPZhiyu() == null ? "0" : noticeBooklet.getPZhiyu().toString());
        wordData.put("pTiyu", noticeBooklet.getPTiyu() == null ? "0" : noticeBooklet.getPTiyu().toString());
        wordData.put("pMeiyu", noticeBooklet.getPMeiyu() == null ? "0" : noticeBooklet.getPMeiyu().toString());
        wordData.put("pLaoyu", noticeBooklet.getPLaoyu() == null ? "0" : noticeBooklet.getPLaoyu().toString());
        wordData.put("pPlan", noticeBooklet.getPPlan() == null ? "无" : noticeBooklet.getPPlan());
        wordData.put("comment", noticeBooklet.getComment() == null ? "无" : noticeBooklet.getComment());
        wordData.put("remark", noticeBooklet.getRemark() == null ? "无" : noticeBooklet.getRemark());

        // 获取当前日期
        LocalDate currentDate = LocalDate.now();
        wordData.put("year", currentDate.getYear());
        wordData.put("month", currentDate.getMonthValue());
        wordData.put("day", currentDate.getDayOfMonth());

        return SingleWordUtils.generateHtmlPreview(wordData);
    }

}