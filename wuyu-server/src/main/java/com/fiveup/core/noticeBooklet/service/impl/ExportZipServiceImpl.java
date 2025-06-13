package com.fiveup.core.noticeBooklet.service.impl;

import com.fiveup.core.noticeBooklet.domain.vo.NoticeBookletVo;
import com.fiveup.core.noticeBooklet.mapper.ExportZipMapper;
import com.fiveup.core.noticeBooklet.service.ExportZipService;
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
public class ExportZipServiceImpl implements ExportZipService {
    private final ExportZipMapper exportZipMapper;

    /**
     * 生成多个Word文件并打包为ZIP
     * @param response HTTP响应对象
     * @param ids 多个通知册的id集合
     */
    @Override
    public void exportMultipleWords(HttpServletResponse response, List<Long> ids) {
        try {
            String path = this.getClass().getClassLoader().getResource("application.yml").getPath();
            String wordPath = FileUtils.getPath(path, FileUtils.WORD_PATH);
            String zipPath = FileUtils.getPath(path, FileUtils.ZIP_PATH);

            // 生成唯一的ZIP文件名
//            String zipFileName = "multiple_words_" + System.currentTimeMillis();

            // 存储所有生成的Word文件路径
            List<String> generatedWordPaths = new ArrayList<>();
            //根据ids集合查询通知册数据集合
            List<NoticeBookletVo> noticeBookletVos = exportZipMapper.getByIds(ids);
            //要处理的Word文件列表，通知册数据集合转成wordFiles集合
            List<Map<String, Object>> wordFiles = new ArrayList<>();
            for (NoticeBookletVo noticeBookletVo : noticeBookletVos) {
                Map<String, Object> map = new HashMap<>();
                map.put("studentName", noticeBookletVo.getStudentName());
                map.put("studentClassNumber", noticeBookletVo.getStudentClassNumber().toString());
                map.put("studentGrade", noticeBookletVo.getStudentGrade().toString());
                map.put("sDeyu", noticeBookletVo.getSDeyu()==null? "0" :noticeBookletVo.getSDeyu().toString());
                map.put("sZhiyu", noticeBookletVo.getSZhiyu()==null? "0" :noticeBookletVo.getSZhiyu().toString());
                map.put("sTiyu", noticeBookletVo.getSTiyu()==null ? "0" :noticeBookletVo.getSTiyu().toString());
                map.put("sMeiyu", noticeBookletVo.getSMeiyu()==null ? "0" :noticeBookletVo.getSMeiyu().toString());
                map.put("sLaoyu", noticeBookletVo.getSLaoyu()==null ? "0" :noticeBookletVo.getSLaoyu().toString());
                map.put("pDeyu", noticeBookletVo.getPDeyu()==null ? "0" :noticeBookletVo.getPDeyu().toString());
                map.put("pZhiyu", noticeBookletVo.getPZhiyu()==null ? "0" :noticeBookletVo.getPZhiyu().toString());
                map.put("pTiyu", noticeBookletVo.getPTiyu()==null ? "0" :noticeBookletVo.getPTiyu().toString());
                map.put("pMeiyu", noticeBookletVo.getPMeiyu()==null ? "0" :noticeBookletVo.getPMeiyu().toString());
                map.put("pLaoyu", noticeBookletVo.getPLaoyu()==null ? "0" :noticeBookletVo.getPLaoyu().toString());
                map.put("comment", noticeBookletVo.getComment()==null?  "无" :noticeBookletVo.getComment());
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
