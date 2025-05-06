package com.fiveup.core.management.model.Req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 尔宣赫
 * @date 2022/3/25
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassAddReq {
    private String grade;
    private String className;
    private Long monitorId;
    private Long schoolId;
}
