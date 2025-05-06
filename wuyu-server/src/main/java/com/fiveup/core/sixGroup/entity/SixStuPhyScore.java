package com.fiveup.core.sixGroup.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
// 某人某体育项多少分
public class SixStuPhyScore {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer phyItemId;
    private Integer score;
    private String studentNum;
}
