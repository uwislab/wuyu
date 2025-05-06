package com.fiveup.core.classManage.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName: ClassReq <br/>
 * Description: <br/>
 * date: 2023/11/18 19:19<br/>
 *
 * @author Administrator<br />
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassReq {
    private int gradeId;
    private int classId;
    private String teacherName;
}
