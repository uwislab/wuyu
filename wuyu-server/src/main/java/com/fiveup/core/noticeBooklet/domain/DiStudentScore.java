package com.fiveup.core.noticeBooklet.domain;

import lombok.Data;

/**
 * @author huangxingcan
 * @date 2025/6/18 11:18
 */
@Data
public class DiStudentScore {
    /**
     * id
     */
    private Integer id;

    /**
     * 学生id
     */
    private Integer studentId;

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
     * 创建时间
     */
    private String sExdate;
}
