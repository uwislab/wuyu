package com.fiveup.core.miniapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

/**
 * @author shilin
 * @date 2022/9/19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassMini {
    private Long id;
    private String grade;
    private String className;
    private String classNum;
    private Long monitorId;
    private Long schoolId;
}
