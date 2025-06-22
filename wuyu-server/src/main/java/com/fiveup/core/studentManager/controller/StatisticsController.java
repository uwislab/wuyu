package com.fiveup.core.studentManager.controller;

import com.fiveup.core.studentManager.service.StatisticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    @Resource
    private StatisticsService statisticsService;

    @GetMapping("/student")
    public Map<String, Object> getStudentStatistics() {
        Map<String, Object> result = new HashMap<>();
        result.put("genderRatio", statisticsService.countGenderRatio());
        result.put("gradeRatio", statisticsService.countGradeRatio());
        result.put("schoolRatio", statisticsService.countSchoolRatio());
        return result;
    }
}