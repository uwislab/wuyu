package com.fiveup.core.management.model.Resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 尔宣赫
 * @date 2022/4/25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Panel {
    private Integer studentNum;
    private Integer teacherNum;
    private Integer classNum;
    private Integer gradeNum;
}
