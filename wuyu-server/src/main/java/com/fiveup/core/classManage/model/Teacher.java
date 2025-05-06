package com.fiveup.core.classManage.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName: Teacher <br/>
 * Description: <br/>
 * date: 2023/11/15 22:59<br/>
 *
 * @author Administrator<br />
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {
    private int id;
    private String teacherName;
    private int gender;
    private String phoneNum;
}
