package com.fiveup.core.cultivation.entity.vo;

import com.fiveup.core.cultivation.entity.BaseEntity;

/**
 * @author Harvi
 */
public class Pager {
    private final int no;
    private final int size;

    public Pager(BaseEntity o) {
        this.no = o.getPageSize() == null ? 10 : o.getPageSize();
        this.size = o.getPageNo() == null ? 1 : o.getPageNo();
    }

    public int getNo() {
        return no;
    }

    public int getSize() {
        return size;
    }
}
