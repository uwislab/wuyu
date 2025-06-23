package com.fiveup.core.fuScale.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * @author shilin
 * @date 2022/9/19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScaleInfo {
    private Integer scaleId;
    private String title;
    private String createDate;
    private Integer state;
    private Integer creatorId;
    private Integer domain;
    private Integer grade;
    private String creatorName;
    private Boolean hasChildren;

    //添加
    private Integer isExecute;
}