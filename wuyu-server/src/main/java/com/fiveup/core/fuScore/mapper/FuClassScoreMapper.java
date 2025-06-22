package com.fiveup.core.fuScore.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fiveup.core.fuScore.entity.po.FuClassScorePO;
import org.apache.ibatis.annotations.Mapper;


/**
 * 该表为班级五育成绩模拟数据，时间跨度为2020年-2023年(FuClassScore)表数据库访问层
 *
 * @author makejava
 * @since 2025-06-19 00:11:48
 */
@Mapper
public interface FuClassScoreMapper extends BaseMapper<FuClassScorePO> {

}

