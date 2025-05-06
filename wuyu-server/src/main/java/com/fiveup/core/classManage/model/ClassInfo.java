package com.fiveup.core.classManage.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 尔宣赫
 * @date 2022/3/23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassInfo {


    private int id;
    private int gradeId; // 年级id
    private int classId; // 班级id
    private String className; // 班级名称
    private int monitor_id; // 班主任id
}
