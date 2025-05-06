package com.fiveup.core.collection.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Copyright (C), 2015-2021, XXX有限公司
 * @ClassName: ColTeacher
 * @Author: 作者姓名(一般是写姓名的拼音)
 * @E-mail: 邮箱
 * @Date: 2021/4/21 10:40
 * @Version: V1.0
 * @Description: 对该类的描述
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sms_teacher")
public class ColTeacher {


    @TableId(value = "id",type = IdType.AUTO)
    private  Long id;

    /**
     * 班级id
     * @return
     * @throws
     */
    private Long classId;

    /**
     * 学校id
     * @return
     * @throws
     */
    private Long schoolId;

    /**
     * 教师姓名
     * @return
     * @throws
     */
    private String name;

    /**
     * 教师性别
     * @return
     * @throws
     */
    private Integer sex;
}
