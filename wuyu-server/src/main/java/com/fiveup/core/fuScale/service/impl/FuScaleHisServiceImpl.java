package com.fiveup.core.fuScale.service.impl;


import com.fiveup.core.fuScale.mapper.FuScaleHisMapper;
import com.fiveup.core.fuScale.mapper.FuScaleHisCacheMapper;
import com.fiveup.core.fuScale.model.*;
import com.fiveup.core.fuScale.service.FuScaleHisService;
import com.fiveup.core.webUser.mapper.WebUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: ParentServiceImpl
 * @Author: shilin
 * @Date: 2022/9/18 16:32
 */
@Service
public class FuScaleHisServiceImpl implements FuScaleHisService {

    @Autowired
    private FuScaleHisMapper fuScaleHisMapper;
    @Autowired
    private FuScaleHisCacheMapper fuScaleHisCacheMapper;

    @Resource
    private WebUserMapper webUserMapper;

    @Override
    public List<ScaleHisInfo> getScaleBySate(Integer stateId) {
        List<ScaleHisInfo> list = fuScaleHisCacheMapper.getScaleByState(stateId);
        list.forEach(s -> {
            s.setCreatorName(fuScaleHisMapper.getCreatorName(s.getCreatorId()));
            s.setUpdateName(fuScaleHisMapper.getCreatorName(s.getUpdateId()));
        });
        return list;
    }

}
