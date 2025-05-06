package com.fiveup.core.questionnaire.po;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Copyright (C), 2015-2021, XXX有限公司
 * @ClassName: Options
 * @Author: 作者姓名(一般是写姓名的拼音)
 * @E-mail: 邮箱
 * @Date: 2021/4/26 12:34
 * @Version: V1.0
 * @Description: 对该类的描述
 */
@Data
@AllArgsConstructor
public class Options {    //这里叫options是因为option是mysql保留关键字
    private Integer questionId;
    private Integer sequence;
    private String content;

    public Options() {
    }
}


