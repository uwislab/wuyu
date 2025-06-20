package com.fiveup.core.fuScore.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fiveup.core.fuScore.entity.po.FuGradeScorePO;
import com.fiveup.core.fuScore.entity.vo.FuClassAvgScoreVO;
import com.fiveup.core.fuScore.entity.vo.FuGradeAvgScoreVO;

import java.util.List;
import java.util.Map;


/**
 * 该表为年级五育成绩模拟数据，时间跨度为2020年-2023年(FuGradeScore)表服务接口
 *
 * @author makejava
 * @since 2025-06-19 00:15:41
 */
public interface FuGradeScoreService extends IService<FuGradeScorePO> {

    /**
     * 获取年级五育成绩
     * @param semester
     * @return
     */
    List<FuGradeAvgScoreVO> getGradeAvgScore(Integer semester);

    List<Map<String,Object>> getGradeInfo();
}

