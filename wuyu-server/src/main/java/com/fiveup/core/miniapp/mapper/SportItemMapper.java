package com.fiveup.core.miniapp.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 史林
 * @date 2022/9/18
 */
@Mapper
@Repository
public interface SportItemMapper {

    @Select("select item_name from basic_sport_item")
    List<String> getItemList();

    @Select("select limitation from basic_sport_item where item_name=#{itemName}")
    String getClassLimit(String itemName);
}
