package com.fiveup.core.fuScore.entity.po;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;

/**
 * 该表为年级五育成绩模拟数据，时间跨度为2020年-2023年(FuGradeScore)表实体类
 *
 * @author makejava
 * @since 2025-06-19 00:15:41
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Builder
@TableName("fu_grade_score")
public class FuGradeScorePO {
    //数据ID    
    @TableId
    private Integer id;

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

