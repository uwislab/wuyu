package com.fiveup.core.management.model.Resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 尔宣赫
 * @date 2022/4/15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeaPageResp {
    private Long teacherId;
    private String teacherName;
    private Integer gender;
    private String phoneNum;
    private String position;
    private String title;
    private String role;
}
