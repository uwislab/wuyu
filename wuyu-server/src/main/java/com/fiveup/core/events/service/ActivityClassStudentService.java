package com.fiveup.core.events.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fiveup.core.events.model.ActivityClassStudent;
import com.fiveup.core.events.model.ActivityStudent;
import com.fiveup.core.management.common.CommonResponse;

import java.util.List;

/**
* @author 86132
* @description 针对表【fu_activity_student】的数据库操作Service
* @createDate 2024-12-02 21:56:52
*/
public interface ActivityClassStudentService extends IService<ActivityClassStudent> {

    // 获取评分
    List<ActivityClassStudent> getActivityDetail(Long activityId, Integer activityAspect);

    // 修改单个学生分数
    Float alterOne(ActivityStudent activityStudent);

    // 教师一次性给大部分学生打分
    CommonResponse checkInActForStu(Long actId, Float score, Long schoolId);

    int getStudentCount(Long activityId);
}
