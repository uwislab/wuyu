package com.fiveup.core.fuScale.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author shilin
 * @date 2022/9/19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScaleHisInfo {
    private Integer id;
    private Integer scaleId;
    private String title;
    private String createDate;
    private Integer state;
    private Integer creatorId;
    private Integer domain;
    private Integer grade;
    private String creatorName;
    private Boolean hasChildren;

    private String updateDate;
    private Integer updateId;
    private String updateName;
    private Integer updateBatch;

    private Integer isExecute;
}