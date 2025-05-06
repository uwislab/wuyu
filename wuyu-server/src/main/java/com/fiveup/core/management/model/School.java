package com.fiveup.core.management.model;

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
public class School {
    private Long id;
    private String schoolName;
    private String address;
}
