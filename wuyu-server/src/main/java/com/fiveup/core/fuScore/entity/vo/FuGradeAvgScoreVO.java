package com.fiveup.core.fuScore.entity.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;

/**
 * @author 云上起了雾 --> 个人博客: lackofcsy.cn
 * @date 2025/06/20 13:46
 **/
@Data
@Builder
public class FuGradeAvgScoreVO {

    /**
     * 年级ID
     */
    private Integer gradeId;

    /**
     * 年级名称
     */
    private String gradeName;

    /**
     * 年级德育总分
     */
    private Integer moralityScore;

    /**
     * 年级智育总分
     */
    private Integer intelligenceScore;

    /**
     * 年级体育总分
     */
    private Integer physicalScore;

    /**
     * 年级美育总分
     */
    private Integer aestheticScore;

    /**
     * 年级劳育总分
     */
    private Integer labourScore;

    /**
     * 得分对应时间（用于区分上下学期）
     */
    private Integer data;
}
