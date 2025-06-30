package com.fiveup.core.studentManager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fiveup.core.studentManager.entity.StudentManager;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface StatisticsMapper extends BaseMapper<StudentManager> {

    // 统计性别比例，结果映射为 {"maleCount": 数量, "femaleCount": 数量} 形式（假设 gender 1 为男，0 为女 ）
    @Select("SELECT " +
            "SUM(CASE WHEN gender = 1 THEN 1 ELSE 0 END) AS maleCount, " +
            "SUM(CASE WHEN gender = 0 THEN 1 ELSE 0 END) AS femaleCount " +
            "FROM basic_student where deleted = 0")
    Map<String, Integer> countGenderRatio();

    // 统计年级比例，按 grade_id 分组统计数量，返回 List<Map> ，每条 Map 包含 gradeId 和 count
    @Select("SELECT grade_id AS gradeId, COUNT(*) AS count FROM basic_student where deleted = 0 GROUP BY grade_id")
    List<Map<String, Object>> countGradeRatio();

    // 统计学校比例，按 school_id 分组统计数量，返回 List<Map> ，每条 Map 包含 schoolId 和 count
    @Select("SELECT school_id AS schoolId, COUNT(*) AS count FROM basic_student where deleted = 0 GROUP BY school_id")
    List<Map<String, Object>> countSchoolRatio();
}
