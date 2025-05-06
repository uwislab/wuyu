package com.fiveup.core.events.controller;


import com.fiveup.core.common.result.Result;
import com.fiveup.core.events.model.ActivityStudent;
import com.fiveup.core.events.model.request.ActivityStudentAddScoreReq;
import com.fiveup.core.events.model.request.AlterStuActReq;
import com.fiveup.core.events.service.ActivityStudentService;
import com.fiveup.core.management.common.CommonResponse;
import com.fiveup.core.management.service.CommonManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author 钟承远
 * @date 2022/4/26
 */
@Slf4j
@RestController
@CrossOrigin
public class
ActivityStudentController {

    @Resource
    private ActivityStudentService activityStudentService;
    @Resource
    private CommonManagementService commonManagementService;//学校id使用

    //教师一次性给大部分学生打分
    @PostMapping("/api/score/together")
    public CommonResponse checkActScoreForStudent(@RequestBody ActivityStudentAddScoreReq req) {
        //校验活动是否存在
        //检验是否已经生成过学生活动成绩了
        try {
            Long actId = req.getActivityId();
            Float score = req.getScore();
            // Long schoolId = commonManagementService.getSchoolId();
            //目前考虑一所学校，id设置为1
            Long schoolId =1L;
            return activityStudentService.checkInActForStu(actId, score, schoolId);
        } catch (Exception e) {
            log.error("checkActScoreForStudent || error={}", e.getMessage(), e);
            return CommonResponse.fail(500, "错误!");
        }
    }

    // 给学生个人打分
    @PutMapping("/api/insertStudentActivityScore")
    public CommonResponse<Void> insertStudentActivityScore(@RequestBody ActivityStudent activityStudent) {
        if(activityStudent.getStudentNum() == null || activityStudent.getActivityId() == null) {
            return CommonResponse.fail(1001,"检查参数");
        }
        try {
            boolean flag=activityStudentService.insertOne(activityStudent);
            if(flag)
                return CommonResponse.ok();
            else
                return CommonResponse.fail(1002,"插入失败，请检查");
        } catch (Exception e) {
            log.error("alterStudentActivityScore_fail || error={}", e.getMessage(), e);
            return CommonResponse.fail(1003, "错误!");
        }
    }

    // 给学生修改个人得分
    @PutMapping("/api/alterStudentActivityScore")
    public CommonResponse<Void> alterStudentActivityScore(@RequestBody ActivityStudent activityStudent) {
        System.out.println(activityStudent.toString());
        if(activityStudent.getStudentNum() == null || activityStudent.getActivityId() == null) {
            return CommonResponse.fail(500,"检查参数");
        }
        try {
            boolean flag=activityStudentService.alterOne(activityStudent);
            System.out.println(flag);
            if(flag)
                return CommonResponse.ok();
            else
                return CommonResponse.fail(500,"修改失败，请检查");
        } catch (Exception e) {
            log.error("alterStudentActivityScore_fail || error={}", e.getMessage(), e);
            return CommonResponse.fail(500, "错误!");
        }
    }
}
