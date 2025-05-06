package com.fiveup.core.webUser.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class webUserList {
    //教师list视图对象
    private List<webUser> list ;
    private int curPage;
    private int pageSize = 5;
    private int pages;
    private int total;
    private boolean isLast;

    public webUserList(List<webUser> list) {
        this.list = list;
    }
}
