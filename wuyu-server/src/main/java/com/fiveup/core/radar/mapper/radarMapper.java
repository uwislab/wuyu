package com.fiveup.core.radar.mapper;


import com.fiveup.core.radar.info.radarInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface radarMapper {

    @Select("select item_content, item_level, item_score, pre_item from fu_item_content where item_level = 1")
    List<radarInfo> getRadarList();
}
