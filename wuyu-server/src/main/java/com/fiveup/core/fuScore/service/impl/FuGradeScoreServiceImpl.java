package com.fiveup.core.fuScore.service.impl;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fiveup.core.fuScore.entity.po.FuGradeScorePO;
import com.fiveup.core.fuScore.entity.vo.FuGradeAvgScoreVO;
import com.fiveup.core.fuScore.mapper.FuGradeScoreMapper;
import com.fiveup.core.fuScore.service.FuGradeScoreService;
import com.fiveup.core.util.CaffeineUtil;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * 该表为年级五育成绩模拟数据，时间跨度为2020年-2023年(FuGradeScore)表服务实现类
 *
 * @author makejava
 * @since 2025-06-19 00:15:41
 */
@Service("fuGradeScoreService")
public class FuGradeScoreServiceImpl extends ServiceImpl<FuGradeScoreMapper, FuGradeScorePO> implements FuGradeScoreService {
    
    private static final List<Integer> DATE_LIST = ListUtil.of( 2020, 2021, 2022);
    @Override
    public List<FuGradeAvgScoreVO> getGradeAvgScore(Integer semester) {
        String cacheKey = "gradeAvgScore:" + semester;
        return CaffeineUtil.get(cacheKey, key -> {
            List<FuGradeScorePO> fuGradeScorePOS = list(
                    new LambdaQueryWrapper<FuGradeScorePO>()
                            .ge(FuGradeScorePO::getData, semester * 100 + 12)
                            .le(FuGradeScorePO::getData, semester * 100 + 107)
                            .orderByAsc(FuGradeScorePO::getGradeId)
            );
            return fuGradeScorePOS.stream()
                    .map(item -> {
                        return FuGradeAvgScoreVO.builder()
                                .gradeId(item.getGradeId())
                                .gradeName(item.getGradeName())
                                .moralityScore(item.getMoralityScore())
                                .intelligenceScore(item.getIntelligenceScore())
                                .physicalScore(item.getPhysicalScore())
                                .aestheticScore(item.getAestheticScore())
                                .labourScore(item.getLabourScore())
                                .data(item.getData())
                                .build();
                    })
                    .collect(Collectors.toList());
        });
    }

    @Override
    public List<Map<String, Object>> getGradeInfo() {
        return CaffeineUtil.get("gradeInfo", key -> {
            List<FuGradeScorePO> gradeInfo = list(
                    new LambdaQueryWrapper<FuGradeScorePO>()
                            .select(FuGradeScorePO::getGradeId, FuGradeScorePO::getGradeName)
                            .groupBy(FuGradeScorePO::getGradeId)
                            .groupBy(FuGradeScorePO::getGradeName)
            );
            return gradeInfo.stream()
                    .map(fuGradeScorePO -> {
                        return MapUtil.<String,Object>builder()
                                .put("gradeId", fuGradeScorePO.getGradeId())
                                .put("gradeName", fuGradeScorePO.getGradeName())
                                .build();
                    })
                    .collect(Collectors.toList());
        });
    }
    
    @PostConstruct
    public void init() {
        CompletableFuture.runAsync(() -> {
            DATE_LIST.forEach(this::getGradeAvgScore);
        });
    }
}