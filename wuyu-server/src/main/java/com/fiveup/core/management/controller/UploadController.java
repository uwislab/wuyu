package com.fiveup.core.management.controller;

import cn.hutool.core.io.resource.InputStreamResource;
import com.fiveup.core.management.common.CommonResponse;
import com.fiveup.core.management.common.enums.BackendErrorCodeEnum;
import com.fiveup.core.management.service.StuService;
import com.google.common.net.HttpHeaders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;

/**
 * @author 尔宣赫
 * @date 2022/4/18
 */
@Slf4j
@Controller
@CrossOrigin
public class UploadController {

    @Resource
    private StuService stuService;

    //    下载上传文件模板
//    @GetMapping("/api/student/getUploadTemplate")
//    public String downloadUploadStudentCSVTemplate() {
////        System.out.println("classpath:/");
//        return "redirect:/managementFile/studentInput.xlsx";
//
//    }
    @GetMapping("/api/student/getUploadTemplate")
    public void downloadFile(HttpServletResponse response) throws IOException {
        byte[] data = getFileData();
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=studentInput.xlsx");
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(data);
        outputStream.flush();


    }
    private byte[] getFileData() throws IOException {
        String filePath = "studentInput.xlsx";
        File file = new File(filePath);
        return Files.readAllBytes(file.toPath());
    }


    // 1.上传学生文件
    @PostMapping("/api/student/uploadStudents")
    @ResponseBody
    public CommonResponse<Void> uploadStudentExt(MultipartFile file) {
        try {
            log.info("uploadStudentExt start execute");
            String flag = stuService.uploadStuIntoDB(file);
            if(flag.contains("成功导入")){
                log.info("uploadStudentExt success");
                return CommonResponse.ok(null, flag);
            }else {
                return CommonResponse.fail(BackendErrorCodeEnum.PARAMS_VALIDATION_ERRNO, flag);
            }
        } catch (Exception e) {
            log.error("uploadStudentExt_fail || error={}", e.getMessage(), e);
            return CommonResponse.fail(BackendErrorCodeEnum.PARAMS_VALIDATION_ERRNO, "系统错误："+e.getMessage());
        }
    }
}
