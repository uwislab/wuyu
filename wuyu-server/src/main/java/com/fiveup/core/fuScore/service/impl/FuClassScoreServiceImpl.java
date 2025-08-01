package com.fiveup.core.fuScore.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fiveup.core.fuScore.entity.po.FuClassScorePO;
import com.fiveup.core.fuScore.entity.po.FuGradeScorePO;
import com.fiveup.core.fuScore.entity.vo.FuClassAvgScoreVO;
import com.fiveup.core.fuScore.entity.vo.FuGradeAvgScoreVO;
import com.fiveup.core.fuScore.mapper.FuClassScoreMapper;
import com.fiveup.core.fuScore.service.FuClassScoreService;
import com.fiveup.core.util.CaffeineUtil;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * 该表为班级五育成绩模拟数据，时间跨度为2020年-2023年(FuClassScore)表服务实现类
 *
 * @author makejava
 * @since 2025-06-19 00:11:51
 */
@Service("fuClassScoreService")
public class FuClassScoreServiceImpl extends ServiceImpl<FuClassScoreMapper, FuClassScorePO> implements FuClassScoreService {
    private static final List<Integer> DATE_LIST = ListUtil.of( 2020, 2021, 2022);

    @Override
    public List<FuClassAvgScoreVO> getClassAvgScore(Integer semester, String clazz) {
        String cacheKey = "classAvgScore:" + semester + ":" + clazz;
        return CaffeineUtil.get(cacheKey, key -> {
            List<FuClassScorePO> fuClassScorePOS = list(
                    new LambdaQueryWrapper<FuClassScorePO>()
                            .eq(FuClassScorePO::getClassName, clazz)
                            .ge(FuClassScorePO::getData, semester * 100 + 12)
                            .le(FuClassScorePO::getData, semester * 100 + 107)
                            .orderByAsc(FuClassScorePO::getGradeId)
            );
            return fuClassScorePOS.stream().map(item -> {
                return FuClassAvgScoreVO.builder()
                        .gradeId(item.getGradeId())
                        .classId(item.getClassId())
                        .className(item.getClassName())
                        .moralityScore(item.getMoralityScore())
                        .intelligenceScore(item.getIntelligenceScore())
                        .physicalScore(item.getPhysicalScore())
                        .aestheticScore(item.getAestheticScore())
                        .labourScore(item.getLabourScore())
                        .data(item.getData())
                        .build();
            }).collect(Collectors.toList());
        });
    }

    @Override
    public List<String> getClassInfo() {
        return CaffeineUtil.get("classInfo", key -> {
            return list(
                    new LambdaQueryWrapper<FuClassScorePO>()
                            .select(FuClassScorePO::getClassName)
                            .groupBy(FuClassScorePO::getClassName)
            ).stream()
                    .map(FuClassScorePO::getClassName)
                    .collect(Collectors.toList());
        });
    }
    
    @PostConstruct
    public void init() {
        CompletableFuture.runAsync(() -> {
            List<String> classInfo = getClassInfo();
            DATE_LIST.forEach(item -> {
                classInfo.forEach(className -> {
                    getClassAvgScore(item, className);
                });
            });
        });
    }
}

