package com.fiveup.core.fuScore.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fiveup.core.fuScore.entity.po.FuClassScorePO;
import com.fiveup.core.fuScore.entity.vo.FuClassAvgScoreVO;
import com.fiveup.core.fuScore.entity.vo.FuGradeAvgScoreVO;

import java.util.List;


/**
 * 该表为班级五育成绩模拟数据，时间跨度为2020年-2023年(FuClassScore)表服务接口
 *
 * @author makejava
 * @since 2025-06-19 00:11:51
 */
public interface FuClassScoreService extends IService<FuClassScorePO> {

    List<FuClassAvgScoreVO> getClassAvgScore(Integer semester, String clazz);
}

