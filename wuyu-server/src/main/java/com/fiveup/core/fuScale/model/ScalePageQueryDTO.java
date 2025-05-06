package com.fiveup.core.fuScale.model;


import lombok.Data;

import java.io.Serializable;

@Data
public class ScalePageQueryDTO implements Serializable {
    private Integer page;

    private Integer pageSize;

    private String title;

    private Integer stateId;

    private Integer creatorId;

    private String createDate;

    //创建人名字
    private String creatorName;

    private Integer state;
}
