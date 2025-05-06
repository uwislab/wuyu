package com.fiveup.core.management.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 尔宣赫
 * @date 2022/3/22
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StuDTO {

    private Long studentId;
    private String studentNum;
    private String studentName;
    private Integer gender;
    private Integer gradeId;
    private Integer classId;
    private Integer scoreInclination;
    private String parentPhoneNum;
    private String schoolName;
    private Integer deleted;
    private Integer isreview;
    private Integer isenter;
}
