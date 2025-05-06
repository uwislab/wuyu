package com.fiveup.core.questionnaire.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 史林
 * @date 2022/9/18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherInfo {
    private String username;
    private String identity;
    private int authority;
}
