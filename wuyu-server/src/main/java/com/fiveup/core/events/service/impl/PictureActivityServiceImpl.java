package com.fiveup.core.events.service.impl;

import com.fiveup.core.events.mapper.PictureActivityMapper;
import com.fiveup.core.events.model.Card;
import com.fiveup.core.events.model.PictureActivity;
import com.fiveup.core.events.service.PictureActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 钟承远
 * @date 2022/4/12
 */
@Slf4j
@Service
public class PictureActivityServiceImpl implements PictureActivityService {
    @Resource
    private PictureActivityMapper pictureActivityMapper;

    @Override
    public void insertOne(PictureActivity pictureActivity) {
        pictureActivityMapper.insertOne(pictureActivity);
    }

    @Override
    public List<String> getAllActPicUrlList() {
        List<String> allUrlList = pictureActivityMapper.getAllUrlList();

        return allUrlList;
    }


}
