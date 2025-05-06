package com.fiveup.core.management.controller;

import com.alibaba.excel.EasyExcel;
import com.fiveup.core.management.model.excel.StuDownloadExt;
import com.fiveup.core.management.model.excel.TeaExt;
import com.fiveup.core.management.service.CommonManagementService;
import com.fiveup.core.management.service.StuService;
import com.fiveup.core.management.service.TeaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author 尔宣赫
 * @date 2022/4/16
 */
@Slf4j
@RestController
@CrossOrigin
public class DownloadController {


    @Resource
    private StuService stuService;

    @Resource
    private TeaService teaService;

    @Resource
    private CommonManagementService commonManagementService;

    //导出学生文档
//    @GetMapping("/api/student/download")
//    public void downloadStudent(@RequestParam(value = "keyword", required = false) String keyword,
//                                @RequestParam(value = "gender", required = false) Integer gender,
//                                @RequestParam(value = "classId", required = false) Long classId,
//                                final HttpServletRequest request,
//                                final HttpServletResponse response)
    @GetMapping("/api/student/download")
    public void downloadStudent(@RequestParam(value = "keyword", required = false) String keyword,
                                @RequestParam(value = "gender", required = false) Integer gender,
                                @RequestParam(value = "gradeId", required = false) Integer gradeId,
                                @RequestParam(value = "classId", required = false) Integer classId,
                                final HttpServletRequest request,
                                final HttpServletResponse response) {
        System.out.println(keyword);

        Long schoolId = commonManagementService.getSchoolId();
        List<StuDownloadExt> stuDownloadExtList = stuService.getStudentDownloadExtList(keyword, gender,gradeId, classId, schoolId);
//        for (StuDownloadExt stuDownloadExt : stuDownloadExtList) {
//            String temp = stuDownloadExt.getClassId()+"班";
//            stuDownloadExt.setClassId(temp);
//            temp = stuDownloadExt.getGradeId()+"年级";
//            stuDownloadExt.setGradeId(temp);
//        }
        String fileName = "studentOutput" + ".xlsx";
        OutputStream os = null;
        try {
            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;fileName=" + fileName + ";filename*=utf-8''" + URLEncoder.encode(fileName, "utf-8"));
            response.setHeader("Access-Control-Allow-Origin", "*");
            os = response.getOutputStream();
            EasyExcel.write(os, StuDownloadExt.class).sheet("sheet1").doWrite(stuDownloadExtList);

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

    @GetMapping("/api/class/downloadStudentInfo")
    public void downloadClassStudents(@RequestParam(value = "gradeId") Integer gradeId,
                                      @RequestParam(value = "classId") Integer classId,
                                      final HttpServletRequest request,
                                      final HttpServletResponse response) {
        this.downloadStudent(null, null, gradeId,classId, request, response);
    }


    @GetMapping("/api/teacher/downloadTeacherInfo")
    public void downloadTeacherInfo(@RequestParam(value = "teacherName", required = false) String teacherName,
                                    @RequestParam(value = "title", required = false) String title,
                                    @RequestParam(value = "position", required = false) String position,
                                    final HttpServletRequest request,
                                    final HttpServletResponse response) {
        Long schoolId = commonManagementService.getSchoolId();
        List<TeaExt> teaExtList = teaService.getTeacherExtList(teacherName, title, position, schoolId);
        String fileName = "teacherOutput" + ".xlsx";
        OutputStream os = null;
        try {
            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;fileName=" + fileName + ";filename*=utf-8''" + URLEncoder.encode(fileName, "utf-8"));
            response.setHeader("Access-Control-Allow-Origin", "*");
            os = response.getOutputStream();
            EasyExcel.write(os, TeaExt.class).sheet("sheet1").doWrite(teaExtList);
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
