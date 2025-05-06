package com.fiveup.core.remark.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("re_feedback")
@Data
public class feedback {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer rate;
    private Integer sid;
    private Integer tid;
    private String fobject;
    private String hobject;
    private String feedback;
    private String name;
}
