package com.fiveup.core.studentManager.mapper;

import com.fiveup.core.studentManager.pojo.StudentManagerQuery;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface StatisticsMapper {
    // 全局统计方法（不变）
    Map<String, Integer> countGenderRatio();
    List<Map<String, Object>> countGradeRatio();
    List<Map<String, Object>> countSchoolRatio();

    // 筛选统计方法 - 参数类型改为 StudentManagerQuery
    Map<String, Integer> countFilteredGenderRatio(StudentManagerQuery query);
    List<Map<String, Object>> countFilteredGradeRatio(StudentManagerQuery query);
    List<Map<String, Object>> countFilteredSchoolRatio(StudentManagerQuery query);
}