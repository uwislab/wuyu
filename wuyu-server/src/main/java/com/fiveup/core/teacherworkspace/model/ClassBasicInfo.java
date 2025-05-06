package com.fiveup.core.teacherworkspace.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author shilin
 * @date 2022/9/19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassBasicInfo {
    private Long id;
    private String grade;
    private String className;
    private int studentCount;
}