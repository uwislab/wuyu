package com.fiveup.core.demonstrate.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 13组编写
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Class13 {
    private String id;
    private String grade;
    private int class_num;
    private String class_name;
    private String monitor_id;
    private int deleted;
    private String school_id;
}
