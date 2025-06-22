package com.fiveup.core.fuScore.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fiveup.core.fuScore.entity.po.FuGradeScorePO;
import org.apache.ibatis.annotations.Mapper;


/**
 * 该表为年级五育成绩模拟数据，时间跨度为2020年-2023年(FuGradeScore)表数据库访问层
 *
 * @author makejava
 * @since 2025-06-19 00:15:41
 */
@Mapper
public interface FuGradeScoreMapper extends BaseMapper<FuGradeScorePO> {

}

