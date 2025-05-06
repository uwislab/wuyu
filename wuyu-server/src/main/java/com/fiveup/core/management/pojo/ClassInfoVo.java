package com.fiveup.core.management.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 龙江威
 * @Date 2023/11/27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassInfoVo {
    private Long classId;//班级id
    private Long grade;
    private String className;//如:一年级/3班
}
