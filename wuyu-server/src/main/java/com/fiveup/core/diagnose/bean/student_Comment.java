package com.fiveup.core.diagnose.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class student_Comment {
    private Long pId;
    private String comment;
    private LocalDateTime updateTime;
}
