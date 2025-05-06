package com.fiveup.core.miniapp.service.impl;

import com.fiveup.core.miniapp.mapper.ActivityScoreMapper;
import com.fiveup.core.miniapp.model.ActivityFamily;
import com.fiveup.core.miniapp.model.ActivityStudent;
import com.fiveup.core.miniapp.service.ActivityScoreService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ActivityScoreServiceImpl implements ActivityScoreService {

    @Resource
    private ActivityScoreMapper activityScoreMapper;

    @Override
    public List<ActivityFamily> getActivityListByClassId(String classId) {
        List<ActivityFamily> activityListResp = activityScoreMapper.getActivitiesByClassId(classId);
        return activityListResp;
    }

    @Override
    public ActivityStudent getActivitiesByStudentNumAndActivityId(ActivityStudent activityStudent) {
        return activityScoreMapper.getActivitiesByStudentNumAndActivityId(activityStudent);
    }

    @Override
    public int familyAddActivityScore(ActivityStudent activityStudent) {
        return activityScoreMapper.familyAddActivityScore(activityStudent);
    }

    @Override
    public int familyUpdateActivityScore(ActivityStudent activityStudent) {
        return activityScoreMapper.familyUpdateActivityScore(activityStudent);
    }

}
