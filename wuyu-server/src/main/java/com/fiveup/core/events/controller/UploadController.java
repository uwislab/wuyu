package com.fiveup.core.events.controller;

import com.fiveup.core.events.service.ActivityStudentService;
import com.fiveup.core.management.common.CommonResponse;
import com.fiveup.core.management.common.enums.BackendErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author 钟承远
 * @date 2022/4/29
 */
@Slf4j
@RestController("uploadActivityStudentScoreBean")
@CrossOrigin
public class UploadController {
    @Resource
    private ActivityStudentService activityStudentService;

    @PostMapping("/api/uploadStudentScore")
    public CommonResponse<Void> uploadStudentActivityScore(MultipartFile file) {
        try {
            activityStudentService.uploadStuIntoDB(file);
            return CommonResponse.ok();
        } catch (Exception e) {
            log.error("uploadStudentExt_fail || error={}", e.getMessage(), e);
            return CommonResponse.fail(BackendErrorCodeEnum.UNSPECIFIED);
        }
    }
}
