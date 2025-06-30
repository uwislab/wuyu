package com.fiveup.core.studentManager.service;

import com.fiveup.core.studentManager.pojo.StudentManagerQuery;
import java.util.Map;

public interface StatisticsService {
    /**
     * 获取全局学生统计数据
     */
    Map<String, Object> getGlobalStatistics();

    /**
     * 根据筛选条件获取学生统计数据
     */
    Map<String, Object> getFilteredStatistics(StudentManagerQuery query);
}