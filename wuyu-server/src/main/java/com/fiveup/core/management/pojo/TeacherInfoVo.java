package com.fiveup.core.management.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author 龙江威
 * @Date 2023/11/27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherInfoVo {
    //详细信息
    private Long id;//教师编号
    private String teacherName;//教师名称
    private Integer gender;//性别表示
    private String phoneNum;
    private String position;//职位
    private String title;//职称
    private String role;//角色
    private Integer deleted;
    private Long schoolId;
    private String monitorName;
    private List<ClassInfoVo> classList;//所教班级
}
