package com.fiveup.core.teacher.entity;

import com.fiveup.core.management.pojo.TeacherVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherList {
    //教师list视图对象
    private List<teacher> list ;
    private int curPage;
    private int pageSize = 5;
    private int pages;
    private int total;
    private boolean isLast;

    public TeacherList(List<teacher> list) {
        this.list = list;
    }
}
