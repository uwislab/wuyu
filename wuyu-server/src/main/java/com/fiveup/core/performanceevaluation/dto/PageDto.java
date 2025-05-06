package com.fiveup.core.performanceevaluation.dto;

/**
 * 接收分页参数对象
 */
public class PageDto {
    // 当前页
    private Integer currentPage;
    // 分页大小
    private Integer pageSize;

    public PageDto() {
    }

    public PageDto(Integer currentPage, Integer pageSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
