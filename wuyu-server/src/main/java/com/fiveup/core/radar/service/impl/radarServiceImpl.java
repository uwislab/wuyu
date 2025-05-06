package com.fiveup.core.radar.service.impl;


import com.fiveup.core.radar.info.radarInfo;
import com.fiveup.core.radar.mapper.radarMapper;
import com.fiveup.core.radar.service.radarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class radarServiceImpl implements radarService {

    @Autowired
    private radarMapper radarMapper;
    @Override
    public List<radarInfo> getRadarList() {
        List<radarInfo> list = radarMapper.getRadarList();
        for (int i = 0; i < list.size(); i++) {
            radarInfo info = list.get(i);
            if(info.getPreItem() == null){
                list.get(i).setPreItem("没有前置指标");
            }
        }
        return list;
    }
}
