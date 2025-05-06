package com.fiveup.core.events.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fiveup.core.events.model.ActivityClassStudent;
import com.fiveup.core.events.model.ActivityStudent;
import com.fiveup.core.events.model.request.ActivityStudentAddScoreReq;
import com.fiveup.core.events.model.response.ActivityDetailResp;
import com.fiveup.core.events.service.ActivityClassStudentService;
import com.fiveup.core.events.service.ActivityStudentService;
import com.fiveup.core.management.common.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author LiuYang
 * @version 1.0
 * @date 2024-12-02 21:53
 * @description TODO
 */
@Slf4j
@RestController("actClassController")
@RequestMapping("/api/manageAct")
@CrossOrigin
public class ActivityClassStudentController {

    @Resource
    private ActivityClassStudentService activityStudentService;

    /**
     * 获取评分
     * @param activityId
     * @param activityAspect
     * @return
     */
    @GetMapping("/getActivityClassStudent")
    public CommonResponse<List<ActivityClassStudent>> getActivityDetail(@RequestParam("activityId") Long activityId,
                                                                        @RequestParam(value = "activityAspect", required = false) Integer activityAspect) {
        List<ActivityClassStudent> studentList =  activityStudentService.getActivityDetail(activityId, activityAspect);

        return CommonResponse.ok(studentList);
    }

    @PutMapping("/alterStudentActivityScore")
    public CommonResponse<Float> alterStudentActivityScore(@RequestBody ActivityStudent activityStudent) {
        if(activityStudent.getStudentNum() == null || activityStudent.getActivityId() == null) {
            return CommonResponse.fail(500,"检查参数");
        }
        try {
            Float aFloat = activityStudentService.alterOne(activityStudent);
            if(aFloat != null)
                return CommonResponse.ok(aFloat);
            else
                return CommonResponse.fail(500,"修改失败，请检查");
        } catch (Exception e) {
            log.error("alterStudentActivityScore_fail || error={}", e.getMessage(), e);
            return CommonResponse.fail(500, "错误!");
        }
    }

    //教师一次性给大部分学生打分
    @PostMapping("/together")
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

    /**
     * 根据名字获取学生信息
     * @param studentName
     * @param activityId
     * @return
     */
    @PutMapping("/api/getStudentByName")
    public CommonResponse<List<ActivityClassStudent>> getStudentByName(String studentName, Long activityId) {
        if(studentName == null || activityId == null) {
            return CommonResponse.fail(500,"检查参数");
        }
        try {
            LambdaQueryWrapper<ActivityClassStudent> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ActivityClassStudent::getActivityId, activityId);
            queryWrapper.like(ActivityClassStudent::getStudentName, studentName);
            List<ActivityClassStudent> activityClassStudents = activityStudentService.list(queryWrapper);
            return CommonResponse.ok(activityClassStudents);
        } catch (Exception e) {
            log.error("alterStudentActivityScore_fail || error={}", e.getMessage(), e);
            return CommonResponse.fail(500, "错误!");
        }
    }
}
