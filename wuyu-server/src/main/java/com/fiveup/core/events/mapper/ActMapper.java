package com.fiveup.core.events.mapper;

import com.fiveup.core.events.model.Card;
import com.fiveup.core.events.model.request.ActivityAddReq;
import com.fiveup.core.events.model.request.ActivityEditReq;
import com.fiveup.core.events.model.response.ActivityDetailResp;
import com.fiveup.core.events.model.response.ActivityPageResp;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ActMapper {

    void insertOne(ActivityAddReq activityAddReq);

    List<ActivityPageResp> getActivitiesByDynamicCondition(String activityName,
                                                           Integer activityState,
                                                           String grade,
                                                           Integer activityType,
                                                           Integer activityAspect,
                                                           Long schoolId,
                                                           List<Integer> classIdList);

    ActivityDetailResp getActivityDetail(Long activityId);

    void edit(ActivityEditReq activityEditReq);

    void deleteOne(Long activityId);


    Integer getEventMonthTotalNum(Date start, Date end);
    Integer getToBeginEventNum();
    Integer getFinishedEventNum();
    Integer getAverageEventScore();


    List<Card> getCardList();


    List<ActivityAddReq> getActivityListByGrade(@Param(value ="actId")Long actId, @Param(value ="grade")String grade);

    void updateScore(@Param(value ="averageScore")float averageScore, @Param(value ="actId")Long actId);
}
