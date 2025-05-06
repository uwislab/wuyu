package com.fiveup.core.management.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageDto1 implements Serializable {
    //前端请求实体
    private int pageNum=1;//当前请求页码
    private int pageSize=5;//页面大小
    private int prePage;//上一次显式的页码

    private String id;//老师ID
    private String teacherName;//老师名称
    private String title;//职级
    private String position;//职位
    private Integer gender;//性别
    private String grade;
}
