package com.fiveup.core.studentManager.service.impl;

import com.fiveup.core.studentManager.mapper.StatisticsMapper;
import com.fiveup.core.studentManager.pojo.StudentManagerQuery;
import com.fiveup.core.studentManager.service.StatisticsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Resource
    private StatisticsMapper statisticsMapper;

    @Override
    public Map<String, Object> getGlobalStatistics() {
        // 复用筛选统计方法，传 null 表示无筛选条件
        return getFilteredStatistics(null);
    }

    @Override
    public Map<String, Object> getFilteredStatistics(StudentManagerQuery query) {
        Map<String, Object> result = new HashMap<>();

        // 调用 Mapper 方法获取统计数据
        result.put("genderRatio", statisticsMapper.countFilteredGenderRatio(query));
        result.put("gradeRatio", statisticsMapper.countFilteredGradeRatio(query));
        result.put("schoolRatio", statisticsMapper.countFilteredSchoolRatio(query));

        return result;
    }
}