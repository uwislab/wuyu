package com.fiveup.core.noticeBooklet.domain.vo;

import lombok.Data;

import java.util.Set;

/**
 * @author huangxingcan
 * @date 2025/6/14 14:14
 */
@Data
public class StudentVO {
    /**
     * 学生学号
     */
    Set<Integer> studentIds;

    /**
     * 班级
     */
    Set<int[]> classNames;

    /**
     * 年级
     */
    Set<Integer> grades;
}
