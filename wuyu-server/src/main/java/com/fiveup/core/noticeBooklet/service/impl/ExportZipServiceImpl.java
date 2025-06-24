package com.fiveup.core.noticeBooklet.service.impl;

import com.fiveup.core.noticeBooklet.domain.NoticeBooklet;
import com.fiveup.core.noticeBooklet.service.ExportZipService;
import com.fiveup.core.noticeBooklet.service.NoticeBookletService;
import com.fiveup.core.noticeBooklet.utils.FileUtils;
import com.fiveup.core.noticeBooklet.utils.WordUtils;
import com.fiveup.core.noticeBooklet.utils.ZipUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
/*
 * 批量导出Word
 * 并压缩成Zip功能
 */
public class ExportZipServiceImpl implements ExportZipService {

    // 注入Service
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

            // 获取application文件保存路径
            String path = this.getClass().getClassLoader().getResource("application.yml").getPath();
            log.info("获取application文件保存路径："+ path);
            // 获取Word文件保存路径
            String wordPath = FileUtils.getPath(path, FileUtils.WORD_PATH);
            log.info("获取ZIP文件保存路径："+ wordPath);
            // 获取ZIP文件保存路径
            String zipPath = FileUtils.getPath(path, FileUtils.ZIP_PATH);
            log.info("获取ZIP文件保存路径" + zipPath);
            // 存储所有生成的Word文件路径
            List<String> generatedWordPaths = new ArrayList<>();
            //根据ids集合查询通知册数据集合
            List<NoticeBooklet> noticeBooklets = noticeBookletService.getNoticeBooklet(null, classId, gradeId, true);
            //要处理的Word文件列表，通知册数据集合转成wordFiles集合
            List<Map<String, Object>> wordFiles = new ArrayList<>();
            //遍历通知册数据集合
            for (NoticeBooklet noticeBooklet : noticeBooklets) {
                //创建一个Map对象，用于存储Word文件数据
                Map<String, Object> map = new HashMap<>();
                //学生姓名
                String studentName = noticeBooklet.getStudentName();
                if(studentName == null){
                    continue;
                }
                map.put("studentName", studentName);
                //班级编号
                String studentClassNumber = noticeBooklet.getStudentClassNumber().toString();
                map.put("studentClassNumber", studentClassNumber);
                //年级
                String studentGrade = noticeBooklet.getStudentGrade().toString();
                map.put("studentGrade", studentGrade);
                //德育
                String sDeyu = noticeBooklet.getSDeyu()==null? "0" :noticeBooklet.getSDeyu().toString();
                map.put("sDeyu", sDeyu);
                //智育
                String sZhiyu = noticeBooklet.getSZhiyu()==null? "0" :noticeBooklet.getSZhiyu().toString();
                map.put("sZhiyu", sZhiyu);
                //体育
                String sTiyu = noticeBooklet.getSTiyu()==null? "0" :noticeBooklet.getSTiyu().toString();
                map.put("sTiyu", sTiyu);
                //美育
                String sMeiyu = noticeBooklet.getSMeiyu()==null? "0" :noticeBooklet.getSMeiyu().toString();
                map.put("sMeiyu", sMeiyu);
                //劳育
                String sLaoyu = noticeBooklet.getSLaoyu()==null? "0" :noticeBooklet.getSLaoyu().toString();
                map.put("sLaoyu", sLaoyu);
                //德育
                String pDeyu = noticeBooklet.getPDeyu()==null? "0" :noticeBooklet.getPDeyu().toString();
                map.put("pDeyu", pDeyu);
                //智育
                String pZhiyu = noticeBooklet.getPZhiyu()==null? "0" :noticeBooklet.getPZhiyu().toString();
                map.put("pZhiyu", pZhiyu);
                //体育
                String pTiyu = noticeBooklet.getPTiyu()==null? "0" :noticeBooklet.getPTiyu().toString();
                map.put("pTiyu", pTiyu);
                //美育
                String pMeiyu = noticeBooklet.getPMeiyu()==null? "0" :noticeBooklet.getPMeiyu().toString();
                map.put("pMeiyu", pMeiyu);
                //劳育
                String pLaoyu = noticeBooklet.getPLaoyu()==null? "0" :noticeBooklet.getPLaoyu().toString();
                map.put("pLaoyu", pLaoyu);
                //评语
                String comment = noticeBooklet.getComment()==null? "无" :noticeBooklet.getComment();
                map.put("comment", comment);
                //计划
                String pPlan = noticeBooklet.getPPlan()==null? "无" :noticeBooklet.getPPlan();
                map.put("pPlan", pPlan);
                //备注
                String remark = noticeBooklet.getRemark()==null? "无" :noticeBooklet.getRemark();
                map.put("remark", remark);
                log.info("Word文件数据："+map);
                //添加Word文件数据
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
                        !wordData.containsKey("pLaoyu") || !wordData.containsKey("comment")||
                        !wordData.containsKey("remark")
                ){
                    continue;
                }

                // 添加日期信息
                // 获取当前年份
                wordData.put("year", DateFormatUtils.format(new Date(), "yyyy"));
                // 获取当前月份
                wordData.put("month", DateFormatUtils.format(new Date(), "MM"));
                // 获取当前日
                wordData.put("day", DateFormatUtils.format(new Date(), "dd"));
                log.info("Word文件数据：" + wordData);
                // 生成Word文件
                WordUtils.saveWord(wordData, wordPath);
                log.info("Word文件生成成功：" + wordPath);
            }

            //zip文件名字
            String zipName = gradeId + "年级" + classId + "班" + "学生通知册";
            //如果班级编号或者年级为空
            if(gradeId == null){
                zipName = classId + "班" + "学生通知册";
            }
            //如果班级编号或者年级为空
            if(classId == null){
                zipName = gradeId + "年级" + "学生通知册";
            }
            //生成zip文件
            ZipUtils.saveZip(wordPath,zipPath,zipName,response);
        } catch (Exception e) {
            //抛出异常
            e.printStackTrace();
            //响应错误
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}
