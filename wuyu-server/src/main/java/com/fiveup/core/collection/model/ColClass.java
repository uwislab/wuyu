package com.fiveup.core.collection.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Copyright (C), 2015-2021, XXX有限公司
 * @ClassName: ColClass
 * @Author: 作者姓名(一般是写姓名的拼音)
 * @E-mail: 邮箱
 * @Date: 2021/4/21 11:07
 * @Version: V1.0
 * @Description: 对该类的描述
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sms_class")
public class ColClass {

    private static final long serialVersionUID = 1L;


    /**
     * 班级id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 老师id
     */
    private Long teacherId;

    /**
     * 年级(入学年份)
     */
    private Integer grade;

    /**
     * 班级名字
     */
    private String name;

    /**
     * 学校id
     */
    private Long schoolId;
}
