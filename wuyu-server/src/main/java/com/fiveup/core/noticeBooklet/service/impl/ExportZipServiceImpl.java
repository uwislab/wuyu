package com.fiveup.core.noticeBooklet.service.impl;

import com.fiveup.core.noticeBooklet.domain.NoticeBooklet;
import com.fiveup.core.noticeBooklet.service.ExportZipService;
import com.fiveup.core.noticeBooklet.service.NoticeBookletService;
import com.fiveup.core.noticeBooklet.utils.FileUtils;
import com.fiveup.core.noticeBooklet.utils.WordUtils;
import com.fiveup.core.noticeBooklet.utils.ZipUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
@RequiredArgsConstructor
//你好、测试
public class ExportZipServiceImpl implements ExportZipService {
    private final NoticeBookletService noticeBookletService;
    /**
     * 生成多个Word文件并打包为ZIP
     * @param response HTTP响应对象
     * @param classId 班级id
     * @param gradeId 年级 id
     */
    @Override
    public void exportMultipleWords(HttpServletResponse response, Integer classId, Integer gradeId) {
        try {
            String path = this.getClass().getClassLoader().getResource("application.yml").getPath();
            String wordPath = FileUtils.getPath(path, FileUtils.WORD_PATH);
            String zipPath = FileUtils.getPath(path, FileUtils.ZIP_PATH);

            // 生成唯一的ZIP文件名
//            String zipFileName = "multiple_words_" + System.currentTimeMillis();

            // 存储所有生成的Word文件路径
            List<String> generatedWordPaths = new ArrayList<>();
            //根据ids集合查询通知册数据集合
            List<NoticeBooklet> noticeBooklets = noticeBookletService.getNoticeBooklet(null, classId, gradeId, true);
            //要处理的Word文件列表，通知册数据集合转成wordFiles集合
            List<Map<String, Object>> wordFiles = new ArrayList<>();
            for (NoticeBooklet noticeBooklet : noticeBooklets) {
                Map<String, Object> map = new HashMap<>();
                map.put("studentName", noticeBooklet.getStudentName());
                map.put("studentClassNumber", noticeBooklet.getStudentClassNumber().toString());
                map.put("studentGrade", noticeBooklet.getStudentGrade().toString());
                map.put("sDeyu", noticeBooklet.getSDeyu()==null? "0" :noticeBooklet.getSDeyu().toString());
                map.put("sZhiyu", noticeBooklet.getSZhiyu()==null? "0" :noticeBooklet.getSZhiyu().toString());
                map.put("sTiyu", noticeBooklet.getSTiyu()==null ? "0" :noticeBooklet.getSTiyu().toString());
                map.put("sMeiyu", noticeBooklet.getSMeiyu()==null ? "0" :noticeBooklet.getSMeiyu().toString());
                map.put("sLaoyu", noticeBooklet.getSLaoyu()==null ? "0" :noticeBooklet.getSLaoyu().toString());
                map.put("pDeyu", noticeBooklet.getPDeyu()==null ? "0" :noticeBooklet.getPDeyu().toString());
                map.put("pZhiyu", noticeBooklet.getPZhiyu()==null ? "0" :noticeBooklet.getPZhiyu().toString());
                map.put("pTiyu", noticeBooklet.getPTiyu()==null ? "0" :noticeBooklet.getPTiyu().toString());
                map.put("pMeiyu", noticeBooklet.getPMeiyu()==null ? "0" :noticeBooklet.getPMeiyu().toString());
                map.put("pLaoyu", noticeBooklet.getPLaoyu()==null ? "0" :noticeBooklet.getPLaoyu().toString());
                map.put("comment", noticeBooklet.getComment()==null?  "无" :noticeBooklet.getComment());
                map.put("pPlan", noticeBooklet.getPPlan()==null? "无" :noticeBooklet.getPPlan());
                map.put("remark", noticeBooklet.getRemark()==null? "无" :noticeBooklet.getRemark());
                wordFiles.add(map);
            }
            // 处理每个Word文件
            for (Map<String, Object> wordData : wordFiles) {
                if(!wordData.containsKey("studentName") || !wordData.containsKey("studentClassNumber") ||
                        !wordData.containsKey("studentGrade") || !wordData.containsKey("sDeyu") ||
                        !wordData.containsKey("sZhiyu") || !wordData.containsKey("sTiyu") ||
                        !wordData.containsKey("sMeiyu") || !wordData.containsKey("sLaoyu") ||
                        !wordData.containsKey("pDeyu") || !wordData.containsKey("pZhiyu") ||
                        !wordData.containsKey("pTiyu") || !wordData.containsKey("pMeiyu") ||
                        !wordData.containsKey("pLaoyu") || !wordData.containsKey("comment")
                ){
                    continue;
                }

                // 添加日期信息
                wordData.put("year", DateFormatUtils.format(new Date(), "yyyy"));
                wordData.put("month", DateFormatUtils.format(new Date(), "MM"));
                wordData.put("day", DateFormatUtils.format(new Date(), "dd"));

                // 生成Word文件
                WordUtils.saveWord(wordData, wordPath);
            }
            ZipUtils.saveZip(wordPath,zipPath,"学生通知册",response);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
