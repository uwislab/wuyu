package com.fiveup.core.fuScore.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fiveup.core.fuScore.entity.po.FuGradeScorePO;
import com.fiveup.core.fuScore.mapper.FuGradeScoreMapper;
import com.fiveup.core.fuScore.service.FuGradeScoreService;
import org.springframework.stereotype.Service;

/**
 * 该表为年级五育成绩模拟数据，时间跨度为2020年-2023年(FuGradeScore)表服务实现类
 *
 * @author makejava
 * @since 2025-06-19 00:15:41
 */
@Service("fuGradeScoreService")
public class FuGradeScoreServiceImpl extends ServiceImpl<FuGradeScoreMapper, FuGradeScorePO> implements FuGradeScoreService {

}

