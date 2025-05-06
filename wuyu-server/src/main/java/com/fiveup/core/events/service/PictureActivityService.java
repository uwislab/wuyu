package com.fiveup.core.events.service;

import com.fiveup.core.events.model.Card;
import com.fiveup.core.events.model.PictureActivity;

import java.util.List;

/**
 * @author 钟承远
 * @date 2022/4/12
 */
public interface PictureActivityService {

    void insertOne(PictureActivity pictureActivity);

    List<String> getAllActPicUrlList();


}
