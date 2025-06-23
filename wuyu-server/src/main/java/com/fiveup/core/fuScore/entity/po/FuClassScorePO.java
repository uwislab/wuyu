package com.fiveup.core.fuScore.entity.po;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;

/**
 * 该表为班级五育成绩模拟数据，时间跨度为2020年-2023年(FuClassScore)表实体类
 *
 * @author makejava
 * @since 2025-06-19 00:11:48
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Builder
@TableName("fu_class_score")
public class FuClassScorePO {
    //数据ID    
    @TableId
    private Integer id;

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
    
    private Integer isenter;
    
    private Integer isreview;
}

