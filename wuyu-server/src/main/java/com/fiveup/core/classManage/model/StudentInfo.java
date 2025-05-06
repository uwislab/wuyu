package com.fiveup.core.classManage.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 尔宣赫
 * @date 2022/3/23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentInfo {

    private int studentId;
    private String studentName;
    private int gender;
    private int totalNum;
}
