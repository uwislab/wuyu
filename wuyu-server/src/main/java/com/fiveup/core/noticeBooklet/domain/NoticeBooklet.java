package com.fiveup.core.noticeBooklet.domain;


import com.fiveup.core.diagnose.bean.student_score;
import com.fiveup.core.management.model.DTO.StuDTO;
import lombok.Data;

import java.util.List;

/**
 * 通知册实体类
 *
 * @author huangxingcan
 * @date 2025/6/7 17:05
 */
@Data
public class NoticeBooklet {
    /**
     * 学生基本信息
     */
    private StuDTO student;

    /**
     * 学生成绩
     */
    private List<student_score> studentScoreList;

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
