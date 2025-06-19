package com.fiveup.core.noticeBooklet.domain;

import com.fiveup.core.monitor.common.Datetime;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author huangxingcan
 * @date 2025/6/18 11:46
 */
@Data
public class DiStudentPlanComment {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 计划id
     */
    private Integer pId;

    /**
     * 评论内容
     */
    private String comment;

    /**
     * 创建时间
     */
    private LocalDate createTime;

    /**
     * 修改时间
     */
    private LocalDate updateTime;
}
