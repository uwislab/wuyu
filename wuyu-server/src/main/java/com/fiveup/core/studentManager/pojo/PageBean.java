package com.fiveup.core.studentManager.pojo;

import java.util.List;

public class PageBean<T> {
    private List<T> data;  // 一页的数据
    private int totalNum;  // 总数量
    private int totalPage;  // 总页码
    private int sizeOfPage; // 分页大小
    private int sizeOfCurrPage; // 当前页的数量
    private int page;  // 当前页码

    public PageBean(){
    }

    public PageBean(List<T> data, int totalNum, int totalPage, int sizeOfPage, int sizeOfCurrPage, int page) {
        this.data = data;
        this.totalNum = totalNum;
        this.totalPage = totalPage;
        this.sizeOfPage = sizeOfPage;
        this.sizeOfCurrPage = sizeOfCurrPage;
        this.page = page;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getSizeOfPage() {
        return sizeOfPage;
    }

    public void setSizeOfPage(int sizeOfPage) {
        this.sizeOfPage = sizeOfPage;
    }

    public int getSizeOfCurrPage() {
        return sizeOfCurrPage;
    }

    public void setSizeOfCurrPage(int sizeOfCurrPage) {
        this.sizeOfCurrPage = sizeOfCurrPage;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "data=" + data +
                ", totalNum=" + totalNum +
                ", totalPage=" + totalPage +
                ", sizeOfPage=" + sizeOfPage +
                ", sizeOfCurrPage=" + sizeOfCurrPage +
                ", page=" + page +
                '}';
    }
}
