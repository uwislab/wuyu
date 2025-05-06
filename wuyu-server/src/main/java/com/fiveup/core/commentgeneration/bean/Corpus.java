package com.fiveup.core.commentgeneration.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 语料库
 */
@Data
@TableName("corpus")
public class Corpus {
    // ID
    @TableId(value = "id", type = IdType.AUTO) //设置自增
    private Integer id;
    // 科目ID
    private Integer subjectId;
    // 分数（以此分数为基准）
    private Integer score;
    // 对应的评价词
    private String comment;
    // 逻辑删除的字段
    private Integer tid;
}
