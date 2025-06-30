package com.fiveup.core.studentManager.controller;

import com.fiveup.core.studentManager.pojo.StudentManagerQuery;
import com.fiveup.core.studentManager.service.StatisticsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    @Resource
    private StatisticsService statisticsService;

    /**
     * 获取全局学生统计数据
     */
    @GetMapping("/student")
    public Map<String, Object> getStudentStatistics() {
        return statisticsService.getGlobalStatistics();
    }

    /**
     * 根据筛选条件获取学生统计数据
     */
    @PostMapping("/student/filtered")
    public Map<String, Object> getFilteredStudentStatistics(@RequestBody StudentManagerQuery query) {
        System.out.println("接收到请求");
        return statisticsService.getFilteredStatistics(query);
    }
}