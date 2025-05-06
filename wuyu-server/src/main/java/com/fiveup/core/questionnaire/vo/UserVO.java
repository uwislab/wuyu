package com.fiveup.core.questionnaire.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 登录成员实体类
 * @Data注解在类上, 为类提供读写属性, 此外还提供了 equals()、hashCode()、toString() 方法
 * @AllArgsConstructor 会生成一个包含所有变量，同时如果变量使用了NotNull annotation ， 会进行是否为空的校验，
 * 全部参数的构造函数的自动生成，该注解的作用域也是只有在实体类上，参数的顺序与属性定义的顺序一致
 */
@Data
@AllArgsConstructor
public class UserVO {

    private Integer id;

    private String name;

    private String password;
}

