package com.fiveup.core.miniapp.service;

import com.fiveup.core.miniapp.model.ActivityFamily;
import com.fiveup.core.miniapp.model.ActivityStudent;

import java.util.List;

public interface ActivityScoreService {

    List<ActivityFamily> getActivityListByClassId(String classId);

    ActivityStudent getActivitiesByStudentNumAndActivityId(ActivityStudent activityStudent);

    int familyAddActivityScore(ActivityStudent activityStudent);

    int familyUpdateActivityScore(ActivityStudent activityStudent);

}
