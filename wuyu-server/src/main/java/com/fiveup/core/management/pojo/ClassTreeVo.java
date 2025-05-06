package com.fiveup.core.management.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 龙江威
 * @Date 2023/11/27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassTreeVo {
    //树状结构
    private Long id;//自身的id
    private Long pid;//父id
    private String name;//名称
}
