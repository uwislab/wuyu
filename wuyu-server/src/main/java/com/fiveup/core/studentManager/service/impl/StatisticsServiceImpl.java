package com.fiveup.core.studentManager.service.impl;

import com.fiveup.core.studentManager.entity.StudentManager;
import com.fiveup.core.studentManager.mapper.StatisticsMapper;
import com.fiveup.core.studentManager.service.StatisticsService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Resource
    private StatisticsMapper statisticsMapper;

    @Override
    public Map<String, Integer> countGenderRatio() {
        return statisticsMapper.countGenderRatio();
    }

    @Override
    public Map<Long, Integer> countGradeRatio() {
        List<Map<String, Object>> resultList = statisticsMapper.countGradeRatio();
        Map<Long, Integer> resultMap = new HashMap<>();
        for (Map<String, Object> row : resultList) {
            Long gradeId = Long.parseLong(row.get("gradeId").toString());
            Integer count = Integer.parseInt(row.get("count").toString());
            resultMap.put(gradeId, count);
        }
        return resultMap;
    }

    @Override
    public Map<Long, Integer> countSchoolRatio() {
        List<Map<String, Object>> resultList = statisticsMapper.countSchoolRatio();
        Map<Long, Integer> resultMap = new HashMap<>();
        for (Map<String, Object> row : resultList) {
            Long schoolId = Long.parseLong(row.get("schoolId").toString());
            Integer count = Integer.parseInt(row.get("count").toString());
            resultMap.put(schoolId, count);
        }
        return resultMap;
    }
}