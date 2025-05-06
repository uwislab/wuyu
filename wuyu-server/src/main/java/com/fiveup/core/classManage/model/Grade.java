package com.fiveup.core.classManage.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName: Grade <br/>
 * Description: <br/>
 * date: 2023/11/15 21:24<br/>
 *
 * @author Administrator<br />
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Grade {
    private int id;
    private String grade;
    private int classType;
    private String className;
    private int monitorId;
    private int gradeId;

    public Grade(String grade, int classType, String className, int monitorId, int gradeId) {
        this.grade = grade;
        this.classType = classType;
        this.className = className;
        this.monitorId = monitorId;
        this.gradeId = gradeId;
    }
}
