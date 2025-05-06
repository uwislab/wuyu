package com.fiveup.core.sixGroup.entity;

import lombok.Data;

@Data
public class FuItemContent {
    private Integer id;
    private Integer scaleId;
    // child
    private String itemContent;
    // parent
    private String preItem;
    // 项目总分
    private Integer itemScore;
    private Integer itemLevel;
}
