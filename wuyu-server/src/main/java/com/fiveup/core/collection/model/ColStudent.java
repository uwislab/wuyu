package com.fiveup.core.collection.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Copyright (C), 2015-2021, XXX有限公司
 * @ClassName: ColStudent
 * @Author: zhouz
 * @E-mail: 邮箱
 * @Date: 2021/4/20 19:02
 * @Version: V1.0
 * @Description: 学生表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sms_student")
public class ColStudent {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    /**
     * 学生姓名
     * @return
     * @throws
     */
    private String name;

    /**
     * 学生性别
     * 性别：0->男；1->女
     * @return
     * @throws
     */
    private Integer sex;

    /**
     * 学生学号
     * @return
     * @throws
     */
    private Long sNumber;

    /**
     * 学生班级id
     * @return
     * @throws
     */
    private Long classId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Long getsNumber() {
        return sNumber;
    }

    public void setsNumber(Long sNumber) {
        this.sNumber = sNumber;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }
}
