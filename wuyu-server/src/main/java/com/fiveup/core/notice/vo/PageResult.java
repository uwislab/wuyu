package com.fiveup.core.notice.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {
    private Long total; // 总记录数
    private List<T> records; // 当前页数据
    private Integer pageNum; // 当前页码
    private Integer pageSize; // 每页大小

    // 显式添加公共的 setRecords 方法，确保即便 Lombok 未正确生成也能被访问
    public void setRecords(List<T> records) {
        this.records = records;
    }
}
