package com.fiveup.core.events.service;

import com.fiveup.core.events.model.Card;
import com.fiveup.core.events.model.request.ActivityAddReq;

import com.fiveup.core.events.model.request.ActivityEditReq;
import com.fiveup.core.events.model.response.ActivityDetailResp;
import com.fiveup.core.events.model.response.ActivityPageResp;
import com.fiveup.core.events.model.response.StatisticNumResp;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author 钟承远
 * @date 2022/3/29
 */
public interface ActivityService {

    void addOne(ActivityAddReq activityAddReq);


    PageInfo<ActivityPageResp> getActivityListByPage(String activityName,
                                                     Integer activityState,
                                                     String grade,
                                                     Integer activityType,
                                                     Integer activityAspect,
                                                     Long schoolId,
                                                     List<Integer> classIdList,
                                                     Integer pageNum,
                                                     Integer pageSize);

    ActivityDetailResp getActivityDetail(Long activityId,Integer activityAspect);

    void edit(ActivityEditReq activityEditReq);

    void deleteOne(Long activityId);


    StatisticNumResp getStatisticNum();

    List<Card> getDisplayData();

    // 修改活动总分
    void updateScore(float averageScore, Long actId);
}
