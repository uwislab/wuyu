package com.fiveup.core.collection.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Copyright (C), 2015-2021, XXX有限公司
 * @ClassName: ColScore
 * @Author: 作者姓名(一般是写姓名的拼音)
 * @E-mail: 邮箱
 * @Date: 2021/4/21 8:50
 * @Version: V1.0
 * @Description: 对该类的描述
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("q_stu_info")
public class ColScore {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    /**
     * 学生学号
     * @return
     * @throws
     */
    private Long sNumber;

    /**
     * 学生五育成绩
     * @return
     * @throws
     */
    private Integer deyu;
    private Integer zhiyu;
    private Integer tiyu;
    private Integer meiyu;
    private Integer laoyu;

    /**
     * 家长对学生评价
     * @return
     * @throws
     */
    private String parentEvaluate;

    /**
     * 教师对学生评价
     * @return
     * @throws
     */
    private String teacherEvaluate;
}
