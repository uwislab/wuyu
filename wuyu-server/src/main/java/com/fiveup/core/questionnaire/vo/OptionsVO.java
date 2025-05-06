package com.fiveup.core.questionnaire.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Copyright (C), 2015-2021, XXX有限公司
 * @ClassName: OptionsVO
 * @Author: 作者姓名(一般是写姓名的拼音)
 * @E-mail: 邮箱
 * @Date: 2021/4/26 12:36
 * @Version: V1.0
 * @Description: 对该类的描述
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionsVO {
    private Integer questionId;
    private Integer sequence;      //在这个问题中是第几个选项
    private String content;
}
