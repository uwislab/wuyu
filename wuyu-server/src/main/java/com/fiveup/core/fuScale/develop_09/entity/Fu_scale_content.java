package com.fiveup.core.fuScale.develop_09.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fu_scale_content implements Serializable {
    private Integer id;
    private String title;
    private String create_date;
//    private String finishdate;
    private int state;
//    private String creator;
    private Integer creator_id;
    private int domain;
    private String grade;
}
