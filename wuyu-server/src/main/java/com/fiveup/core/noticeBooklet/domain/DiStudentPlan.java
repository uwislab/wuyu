package com.fiveup.core.noticeBooklet.domain;

import lombok.Data;

/**
 * @author huangxingcan
 * @date 2025/6/18 11:35
 */
@Data
public class DiStudentPlan {
    /**
     * 主键
     */
    private Integer pId;

    /**
     * 德育
     */
    private Integer pDeyu;

    /**
     * 智育
     */
    private Integer pZhiyu;

    /**
     * 体育
     */
    private Integer pTiyu;

    /**
     * 美育
     */
    private Integer pMeiyu;

    /**
     * 劳育
     */
    private Integer pLaoyu;

    /**
     * 计划
     */
    private String pPlan;
}
