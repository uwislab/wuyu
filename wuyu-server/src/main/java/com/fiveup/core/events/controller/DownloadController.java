package com.fiveup.core.events.controller;

import com.alibaba.excel.EasyExcel;
import com.fiveup.core.events.model.ext.ActivityStudentDownloadExt;
import com.fiveup.core.events.service.ActivityService;
import com.fiveup.core.events.service.ActivityStudentService;
import com.fiveup.core.management.service.CommonManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author 钟承远
 * @date 2022/4/27
 */
@Slf4j
@Controller("downloadBean")
@CrossOrigin
public class DownloadController {
    @Resource
    private ActivityService activityService;

    @Resource
    private ActivityStudentService activityStudentService;

    @Resource
    private CommonManagementService commonManagementService;

    @GetMapping("/api/downloadActivity")
    public void downloadStudentScore(@RequestParam("activityId") Long activityId,
                                     final HttpServletRequest request,
                                     final HttpServletResponse response) {

        Long schoolId = commonManagementService.getSchoolId();
        List<ActivityStudentDownloadExt> activityStudentDownloadExtList = activityStudentService.getActivityStudentExtList(activityId, schoolId);
        String fileName = "activityStudentOutput" + ".xlsx";
        OutputStream os = null;
        try {
            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;fileName=" + fileName + ";filename*=utf-8''" + URLEncoder.encode(fileName, "utf-8"));
            response.setHeader("Access-Control-Allow-Origin", "*");
            os = response.getOutputStream();
            EasyExcel.write(os, ActivityStudentDownloadExt.class).sheet("sheet1").doWrite(activityStudentDownloadExtList);
        } catch (Exception e) {
            log.error("excel_file_generate_fail || error={}", e.getMessage(), e);
        } finally {
            if (os != null) {
                try {
                    os.flush();
                    os.close();
                } catch (IOException e) {
                    log.error("output_stream_close_fail || error={}", e.getMessage(), e);
                }
            }
        }
    }

    @GetMapping("/api/scoreTemplate")
    public void downloadActivityStudentScoreTemplateExt(@RequestParam("activityId") Long activityId,
                                                        final HttpServletRequest request,
                                                        final HttpServletResponse response) {
        Long schoolId = commonManagementService.getSchoolId();
        List<ActivityStudentDownloadExt> uploadTemplateExtList = activityStudentService.getUploadTemplate(activityId,schoolId);
        String fileName = "activityStudentTemplateOutput" + ".xlsx";
        OutputStream os = null;
        try {
            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;fileName=" + fileName + ";filename*=utf-8''" + URLEncoder.encode(fileName, "utf-8"));
            response.setHeader("Access-Control-Allow-Origin", "*");
            os = response.getOutputStream();
            EasyExcel.write(os, ActivityStudentDownloadExt.class).sheet("sheet1").doWrite(uploadTemplateExtList);
        } catch (Exception e) {
            log.error("excel_file_generate_fail || error={}", e.getMessage(), e);
        } finally {
            if (os != null) {
                try {
                    os.flush();
                    os.close();
                } catch (IOException e) {
                    log.error("output_stream_close_fail || error={}", e.getMessage(), e);
                }
            }
        }
    }
}

