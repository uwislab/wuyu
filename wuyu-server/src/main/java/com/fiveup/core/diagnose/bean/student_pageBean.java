package com.fiveup.core.diagnose.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class student_pageBean {
    private long total;
    private List rows;
}
