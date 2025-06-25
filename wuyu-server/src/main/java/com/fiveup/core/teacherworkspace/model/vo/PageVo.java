package com.fiveup.core.teacherworkspace.model.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PageVo<T> {
    @ApiModelProperty("当前页数据")
    private List<T> records;
    @ApiModelProperty("总数")
    private Long total;
    @ApiModelProperty("总页数")
    private Long pages;
    @ApiModelProperty("当前页大小")
    private Long size;
    @ApiModelProperty("当前页数")
    private Long page;

    private PageVo(Page<T> page) {
        this.total = page.getTotal();
        this.size = page.getSize();
        this.page = page.getCurrent();
        this.records = page.getRecords();
        this.pages = page.getPages();
    }

    public static <R> PageVo<R> of(Page<R> page) {
        return new PageVo<>(page);
    }
}