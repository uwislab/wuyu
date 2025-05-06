package com.fiveup.core.collection.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Copyright (C), 2015-2021, XXX有限公司
 * @ClassName: ColView
 * @Author: 作者姓名(一般是写姓名的拼音)
 * @E-mail: 邮箱
 * @Date: 2021/5/29 17:35
 * @Version: V1.0
 * @Description: 对该类的描述
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("q_view")
public class ColView {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    private Long sNumber;

    private String name;

    private Long classId;

    private String Theme;

    private String teacherremark;

    private String parentremark;

    private Integer teacherscore;

    private Integer parentscore;

    private String themetype;

    private String themecontent;

    private Integer examJoinScore;
}
