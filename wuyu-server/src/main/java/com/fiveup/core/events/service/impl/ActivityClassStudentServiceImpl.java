package com.fiveup.core.events.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fiveup.core.events.model.ActivityClassStudent;
import com.fiveup.core.events.model.ActivityStudent;
import com.fiveup.core.events.service.ActivityClassStudentService;
import com.fiveup.core.events.mapper.ActivityClassStudentMapper;
import com.fiveup.core.events.service.ActivityService;
import com.fiveup.core.management.common.CommonResponse;
import com.fiveup.core.management.common.enums.BizErrorCodeEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static oshi.util.platform.windows.PerfDataUtil.updateQuery;

/**
* @author 86132
* @description 针对表【fu_activity_student】的数据库操作Service实现
* @createDate 2024-12-02 21:56:52
*/
@Service
public class ActivityClassStudentServiceImpl extends ServiceImpl<ActivityClassStudentMapper, ActivityClassStudent>
    implements ActivityClassStudentService{

    @Resource
    private ActivityClassStudentMapper activityClassMapper;
    @Resource
    private ActivityService activityService;

    @Override
    public List<ActivityClassStudent> getActivityDetail(Long activityId, Integer activityAspect) {
        LambdaQueryWrapper<ActivityClassStudent> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ActivityClassStudent::getActivityId, activityId);
        List<ActivityClassStudent> activityClassStudents = list(queryWrapper);

        return activityClassStudents;
    }

    /**
     * 修改单个学生分数
     * @param activityStudent
     * @return
     */
    @Override
    public Float alterOne(ActivityStudent activityStudent) {
        LambdaUpdateWrapper<ActivityClassStudent> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.eq(activityStudent.getActivityId()!=null, ActivityClassStudent::getActivityId, activityStudent.getActivityId());
        queryWrapper.eq(activityStudent.getStudentNum() != null, ActivityClassStudent::getStudentNum, activityStudent.getStudentNum());
        queryWrapper.set(ActivityClassStudent::getTeacherScore, activityStudent.getStuActScore());
        queryWrapper.set(ActivityClassStudent::getTotalScore, activityStudent.getStuActScore());
        boolean flag = update(queryWrapper);

        float averageScore = 0;
        if (flag) {
            LambdaQueryWrapper<ActivityClassStudent> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(activityStudent.getActivityId() != null, ActivityClassStudent::getActivityId, activityStudent.getActivityId());
            List<ActivityClassStudent> activityClassStudents = list(queryWrapper1);

            AtomicReference<Float> totalScore = new AtomicReference<>((float) 0);
            activityClassStudents = activityClassStudents.stream().map(student -> {
                totalScore.updateAndGet(v -> (float) (v + (float) student.getTotalScore()));
                return student;
            }).collect(Collectors.toList());

            averageScore = totalScore.get() / activityClassStudents.size();

            activityService.updateScore(averageScore, activityStudent.getActivityId());
            return averageScore;
        } else {
            return null;
        }
    }

    /**
     * 教师一次性给大部分学生打分
     * @param actId
     * @param score
     * @param schoolId
     * @return
     */
    @Override
    @Transactional
    public CommonResponse checkInActForStu(Long actId, Float score, Long schoolId) {
        LambdaQueryWrapper<ActivityClassStudent> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(actId!=null, ActivityClassStudent::getActivityId, actId);
        List<ActivityClassStudent> activityClassStudents = list(queryWrapper);

        AtomicReference<Float> totalScore = new AtomicReference<>((float) 0);
        activityClassStudents = activityClassStudents.stream().map(student -> {
            student.setTotalScore(score);
            student.setTeacherScore(score);
            totalScore.updateAndGet(v -> new Float((float) (v + (float) student.getTotalScore())));
            return student;
        }).collect(Collectors.toList());

        float averageScore = totalScore.get() / activityClassStudents.size();

        activityService.updateScore(averageScore, actId);

        boolean b = saveOrUpdateBatch(activityClassStudents);
        if (b) {
            return CommonResponse.ok(averageScore);
        } else {
            return CommonResponse.fail(BizErrorCodeEnum.valueOf("500"), "请检查网络问题");
        }
    }

    @Override
    public int getStudentCount(Long activityId) {
        LambdaQueryWrapper<ActivityClassStudent> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ActivityClassStudent::getActivityId, activityId);

        return list(queryWrapper).size();
    }
}




