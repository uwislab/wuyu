package com.fiveup.core.management.common.exception;

/**
 * @author 尔宣赫
 * @date 29/11/2021
 */
public interface ErrorCode {

    /**
     * 获取错误码
     * @return
     */
    Integer getCode();

    /**
     * 获取错误信息
     * @return
     */
    String getDescription();
}
