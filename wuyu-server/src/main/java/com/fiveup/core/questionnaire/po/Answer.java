package com.fiveup.core.questionnaire.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Copyright (C), 2015-2021, XXX有限公司
 * @ClassName: Answer
 * @Author: 作者姓名(一般是写姓名的拼音)
 * @E-mail: 邮箱
 * @Date: 2021/4/26 12:21
 * @Version: V1.0
 * @Description: 对该类的描述
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Answer {
    private Integer id;
    private Integer paperId;
    private Integer questionId;
    private Integer questionType;
    private String createTime;
    private String answerContent;
}
