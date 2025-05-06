package com.fiveup.core.management.common;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PageResult<T> {
    @JsonProperty("current_page")
    private Long currentPage;
    @JsonProperty("page_size")
    private Long pageSize;
    @JsonProperty("total")
    private Long total;
    @JsonProperty("list")
    private List<T> list;

    public PageResult() {
    }

    public PageResult(Long currentPage, Long pageSize, Long total, List<T> list) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.total = total;
        this.list = list;
    }

    public PageResult(Long currentPage, Long pageSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    public Long getCurrentPage() {
        return this.currentPage;
    }

    public Long getPageSize() {
        return this.pageSize;
    }

    public Long getTotal() {
        return this.total;
    }

    public List<T> getList() {
        return this.list;
    }

    @JsonProperty("current_page")
    public void setCurrentPage(final Long currentPage) {
        this.currentPage = currentPage;
    }

    @JsonProperty("page_size")
    public void setPageSize(final Long pageSize) {
        this.pageSize = pageSize;
    }

    @JsonProperty("total")
    public void setTotal(final Long total) {
        this.total = total;
    }

    @JsonProperty("list")
    public void setList(final List<T> list) {
        this.list = list;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof PageResult)) {
            return false;
        } else {
            PageResult<?> other = (PageResult)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label59:
                {
                    Object this$currentPage = this.getCurrentPage();
                    Object other$currentPage = other.getCurrentPage();
                    if (this$currentPage == null) {
                        if (other$currentPage == null) {
                            break label59;
                        }
                    } else if (this$currentPage.equals(other$currentPage)) {
                        break label59;
                    }

                    return false;
                }

                Object this$pageSize = this.getPageSize();
                Object other$pageSize = other.getPageSize();
                if (this$pageSize == null) {
                    if (other$pageSize != null) {
                        return false;
                    }
                } else if (!this$pageSize.equals(other$pageSize)) {
                    return false;
                }

                Object this$total = this.getTotal();
                Object other$total = other.getTotal();
                if (this$total == null) {
                    if (other$total != null) {
                        return false;
                    }
                } else if (!this$total.equals(other$total)) {
                    return false;
                }

                Object this$list = this.getList();
                Object other$list = other.getList();
                if (this$list == null) {
                    if (other$list != null) {
                        return false;
                    }
                } else if (!this$list.equals(other$list)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof PageResult;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $currentPage = this.getCurrentPage();
        result = result * 59 + ($currentPage == null ? 43 : $currentPage.hashCode());
        Object $pageSize = this.getPageSize();
        result = result * 59 + ($pageSize == null ? 43 : $pageSize.hashCode());
        Object $total = this.getTotal();
        result = result * 59 + ($total == null ? 43 : $total.hashCode());
        Object $list = this.getList();
        result = result * 59 + ($list == null ? 43 : $list.hashCode());
        return result;
    }

    public String toString() {
        Long var10000 = this.getCurrentPage();
        return "PageResult(currentPage=" + var10000 + ", pageSize=" + this.getPageSize() + ", total=" + this.getTotal() + ", list=" + this.getList() + ")";
    }
}