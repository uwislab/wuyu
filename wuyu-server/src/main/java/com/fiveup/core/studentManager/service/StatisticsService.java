package com.fiveup.core.studentManager.service;

import java.util.Map;

public interface StatisticsService {

    Map<String, Integer> countGenderRatio();

    Map<Long, Integer> countGradeRatio();

    Map<Long, Integer> countSchoolRatio();
}