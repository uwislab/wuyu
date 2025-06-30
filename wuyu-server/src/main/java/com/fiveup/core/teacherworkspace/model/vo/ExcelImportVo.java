package com.fiveup.core.teacherworkspace.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class ExcelImportVo<T, U> {
    /**
     * 缓存id
     */
    @ApiModelProperty("缓存id")
    private Long id;
    /**
     * 成功结果数据列表
     */
    @ApiModelProperty("成功结果数据列表")
    private List<T> successList;
    /**
     * 成功数据数量
     */
    @ApiModelProperty("成功数据数量")
    private Integer successNumber;
    /**
     * 失败结果数据列表
     */
    @ApiModelProperty("失败结果数据列表")
    private List<U> failList;
    /**
     * 失败数据数量
     */
    @ApiModelProperty("失败数据数量")
    private Integer failNumber;
}
