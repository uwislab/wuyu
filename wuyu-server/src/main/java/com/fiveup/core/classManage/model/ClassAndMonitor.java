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
public class ClassAndMonitor {

    private int id;
    private int gradeId;
    private int classId;
    private int gender;
    private String realName;
    private String contactInfo;
    private int identity;
}
