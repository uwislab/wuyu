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
// 班级与教师关联关系，CT - class and teachers
public class CTCorrelation {

    private Integer gradeId;
    private Integer classId;
    private Integer teacherId;
}
