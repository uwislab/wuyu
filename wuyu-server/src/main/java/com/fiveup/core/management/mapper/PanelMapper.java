package com.fiveup.core.management.mapper;

/**
 * @author 尔宣赫
 * @date 2022/5/8
 */
public interface PanelMapper {

    Integer getStuCount(Integer schoolId);
    Integer getTeacherCount(Integer schoolId);
    Integer getClassCount(Integer schoolId);
    Integer getGradeCount(Integer schoolId);

}
