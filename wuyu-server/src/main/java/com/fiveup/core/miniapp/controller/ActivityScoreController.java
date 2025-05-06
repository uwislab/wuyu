package com.fiveup.core.miniapp.controller;

import com.fiveup.core.management.common.CommonResponse;
import com.fiveup.core.miniapp.model.ActivityFamily;
import com.fiveup.core.miniapp.model.ActivityStudent;
import com.fiveup.core.miniapp.service.ActivityScoreService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/minapp")
public class ActivityScoreController {

    @Resource
    private ActivityScoreService activityService;

    @GetMapping(value="/getActivityListByClassId")
    public CommonResponse<List<ActivityFamily>> getActivityListByClassId(@RequestParam("classId") String classId) {
        List<ActivityFamily> activityListResp=activityService.getActivityListByClassId(classId);
        if(activityListResp.size() == 0) {
            return CommonResponse.fail(1001, "未查询到当前学生所在班级举行的活动");
        } else return CommonResponse.ok(activityListResp);
    }

    @PostMapping(value="/familyAddActivityScore")
    public CommonResponse getActivityListByClassId(@RequestBody ActivityStudent activityStudent) {

        ActivityStudent res=activityService.getActivitiesByStudentNumAndActivityId(activityStudent);
         int flag;
         if(res == null){
             activityStudent.setStuActScore(0);
             flag = activityService.familyAddActivityScore(activityStudent);
         }else {
             //教师已评分 设置总分
             if(res.getStuActScore() != 0) {
                 activityStudent.setActivityScoreTotal((activityStudent.getActivityScoreFamily() +
                         res.getStuActScore()) / 2);
             }
             flag = activityService.familyUpdateActivityScore(activityStudent);
         }
        return flag != 0 ? CommonResponse.ok("家长评分活动成功！") : CommonResponse.fail(1001,"家长评分活动失败！");
    }
}
