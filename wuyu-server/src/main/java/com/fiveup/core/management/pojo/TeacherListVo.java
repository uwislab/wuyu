package com.fiveup.core.management.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author 龙江威
 * @Date 2023/11/20
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherListVo {
    //教师list视图对象
    private List<TeacherVo> list ;
    private int curPage;
    private int pageSize = 5;
    private int pages;
    private int total;
    private boolean isLast;

    public TeacherListVo(List<TeacherVo> list) {
        this.list = list;
    }

}
