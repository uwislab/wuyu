package com.fiveup.core.fuScale.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author shilin
 * @date 2022/9/19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScaleContent {
    private int id;
    private int itemId;
    private int scaleId;
    private int itemNum;
    private String itemContent;
    private String preItem;
    private int itemLevel;
    private int itemScore;
    private String evaluationMethod;
    private String evaluators;
    private String remark;
    private Integer preItemId;
    private Boolean hasChildren;

    private List<ScaleContent> children;
}