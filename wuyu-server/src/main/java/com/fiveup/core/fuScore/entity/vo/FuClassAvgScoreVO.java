package com.fiveup.core.fuScore.entity.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;

/**
 * @author 云上起了雾 --> 个人博客: lackofcsy.cn
 * @date 2025/06/20 13:37
 **/
@Data
@Builder
public class FuClassAvgScoreVO {
    
    /**
     * 年级ID
     */
    private Integer gradeId;

    /**
     * 班级ID
     */
    private Integer classId;

    /**
     * 班级名称
     */
    private String className;

    /**
     * 班级德育总分
     */
    private Integer moralityScore;

    /**
     * 班级智育总分
     */
    private Integer intelligenceScore;

    /**
     * 班级体育总分
     */
    private Integer physicalScore;

    /**
     * 班级美育总分
     */
    private Integer aestheticScore;

    /**
     * 班级劳育总分
     */
    private Integer labourScore;

    /**
     * 得分对应时间（用于区分上下学期）
     */
    private Integer data;
}
