package com.fiveup.core.overallOperation.domain;

import lombok.Data;

/**
 * @className: FuStudentDto
 * @author: pjy
 * @date: 2024/12/1
 */
@Data
public class FuStudentDto {
    private StudentScore studentScore;
    private Integer grade;
    private Integer term;
}
