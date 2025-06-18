package com.fiveup.core.noticeBooklet.domain;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 通知册实体类
 *
 * @author huangxingcan
 * @date 2025/6/7 17:05
 */
@Data
public class NoticeBooklet {
    /**
     * 学生学号
     */
    @NotNull(message = "学号不能为空")
    private Integer studentId;

    /**
     * 学生姓名
     */
    @NotBlank(message = "姓名不能为空")
    private String studentName;

    /**
     * 学生班级
     */
    @NotNull(message = "班级不能为空")
    private Integer studentClassNumber;

    /**
     * 学生年级
     */
    @NotNull(message = "年级不能为空")
    private Integer studentGrade;

    /**
     * 德育分
     */
    private Integer sDeyu;

    /**
     * 智育分
     */
    private Integer sZhiyu;

    /**
     * 体育分
     */
    private Integer sTiyu;

    /**
     * 美育分
     */
    private Integer sMeiyu;

    /**
     * 劳育分
     */
    private Integer sLaoyu;

    /**
     * 考试时间
     */
    private String sDate;

    /**
     * 计划德育分
     */
    private Integer pDeyu;

    /**
     * 计划智育分
     */
    private Integer pZhiyu;

    /**
     * 计划体育分
     */
    private Integer pTiyu;

    /**
     * 计划美育分
     */
    private Integer pMeiyu;

    /**
     * 计划劳育分
     */
    private Integer pLaoyu;


    /**
     * 假期要求
     */
    private String pPlan;

    /**
     * 建议
     */
    private String comment;

    /**
     * 评语
     */
    private String remark;

}
