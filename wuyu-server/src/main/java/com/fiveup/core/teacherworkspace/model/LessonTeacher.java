package com.fiveup.core.teacherworkspace.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author shilin
 * @date 2022/9/19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonTeacher {
    private String realName;
    private String identityInfo;
    private int gender;
    private String contactInfo;
}