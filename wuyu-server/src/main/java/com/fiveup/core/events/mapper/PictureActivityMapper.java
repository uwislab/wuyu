package com.fiveup.core.events.mapper;

import com.fiveup.core.events.model.PictureActivity;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PictureActivityMapper {

    void insertOne(PictureActivity pictureActivity);

    
    List<String> getUrlListByActivityId(Long activityId);


    List<String> getAllUrlList();
}
