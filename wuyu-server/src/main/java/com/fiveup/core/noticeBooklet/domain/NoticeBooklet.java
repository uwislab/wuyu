package com.fiveup.core.noticeBooklet.domain;


import lombok.Data;

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
    private Integer studentId;

    /**
     * 学生姓名
     */
    private String studentName;

    /**
     * 学生班级
     */
    private Integer studentClassNumber;

    /**
     * 学生年级
     */
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
     * 评语
     */
    private String remark;

    /**
     * 建议
     */
    private String suggest;

    /**
     * 假期要求
     */
    private String holiday;

}
