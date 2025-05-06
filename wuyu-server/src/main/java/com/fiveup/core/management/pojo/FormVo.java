package com.fiveup.core.management.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author 龙江威
 * @Date 2023/11/26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormVo {
    private List<Integer> gradeList;
    private List<String> positionList;
}
