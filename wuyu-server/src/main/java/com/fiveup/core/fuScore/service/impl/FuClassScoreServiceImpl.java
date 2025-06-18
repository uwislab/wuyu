package com.fiveup.core.fuScore.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fiveup.core.fuScore.entity.po.FuClassScorePO;
import com.fiveup.core.fuScore.mapper.FuClassScoreMapper;
import com.fiveup.core.fuScore.service.FuClassScoreService;
import org.springframework.stereotype.Service;

/**
 * 该表为班级五育成绩模拟数据，时间跨度为2020年-2023年(FuClassScore)表服务实现类
 *
 * @author makejava
 * @since 2025-06-19 00:11:51
 */
@Service("fuClassScoreService")
public class FuClassScoreServiceImpl extends ServiceImpl<FuClassScoreMapper, FuClassScorePO> implements FuClassScoreService {

}

