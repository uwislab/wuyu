package com.fiveup.core.management.model.DTO;

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
public class ClassDTO {

    private Long id;
    private String grade;
    private String className;
    private Long monitorId;
    private Integer deleted;
    private TeaDTO monitor;
}
