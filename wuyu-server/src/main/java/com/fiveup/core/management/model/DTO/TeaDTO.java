package com.fiveup.core.management.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 尔宣赫
 * @date 2022/3/24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeaDTO {
    private Long id;
    private Long teacherId;
    private String teacherName;
    private String monitorName;
    private Integer gender;
    private String phoneNum;
    private String position;
    private String title;
    private String role;
    private Integer deleted;
    private String username;
    private String password;
}
