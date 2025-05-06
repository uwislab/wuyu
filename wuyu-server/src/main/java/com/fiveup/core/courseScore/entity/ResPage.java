package com.fiveup.core.courseScore.entity;

import lombok.Data;

/**
 *分页类：封装分页信息
 * @param <T>
 */
@Data
public class ResPage<T> {
    private Integer page;   // 当前页
    private Integer size;   // 当前页大小
    private Integer totalPage; // 总页数
    private Long total;     // 总条数
    private T data;         // 当前页的数据
}