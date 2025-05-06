package com.fiveup.core.commentgeneration.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fiveup.core.commentgeneration.bean.Subject;
import lombok.Data;

/**
 * 语料库
 */
@Data
@TableName("corpus")
public class CorpusVO {
    // ID
    private Integer id;
    // 科目
    private Subject subject;
    // 分数（以此分数为基准）
    private Integer score;
    // 对应的评价词
    private String comment;
    // 逻辑删除的字段
    private Integer tid;
}
