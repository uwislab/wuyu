package com.fiveup.core.sixGroup.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class IndexScoreQuery {
    private Integer 当前页;
    private Integer 每页数量;
    private Integer year;
    private Integer icId;
    private Integer cls;
    private String studentNum;
    private String studentName;
}
