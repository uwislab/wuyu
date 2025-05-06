package com.fiveup.core.remark.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("re_student")
@Data
public class student {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private Integer virtue;
    private Integer intelligence;
    private Integer sports;
    private Integer art;
    private Integer labor;
    private String remark;
    private Integer tid;
//    增加sid后修改的代码
    private Integer sid;
//    增加sid后修改的代码
}
