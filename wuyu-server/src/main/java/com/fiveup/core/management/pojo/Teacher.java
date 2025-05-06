package com.fiveup.core.management.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 龙江威
 * @Date 2023/11/20
 */

@Data//get,set,以及基本重写
@NoArgsConstructor//无参构造
@AllArgsConstructor//全参构造
public class Teacher {
    //映射basic_teacher表
    private Long id;
    private String teacherName;
    private Integer gender;
    private String phoneNum;
    private String position;
    private String title;
    private String role;
    private Integer deleted;
    private String username;
    private String password;
    private String schoolId;
    //未使用
    private Long teacherId;
    private String monitorName;

}
