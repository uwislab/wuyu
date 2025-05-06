package com.fiveup.core.fuScale.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class Detail {
    private int id;
    private int scaleId;
    private int itemId;
    private String name;
    private String level;
    private int grade;
    private int scoreMin;
    private int scoreMax;
    private int dataMin;
    private int dataMax;
    private Boolean isDataType;
}