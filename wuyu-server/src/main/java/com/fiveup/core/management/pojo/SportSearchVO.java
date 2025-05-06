package com.fiveup.core.management.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data//get,set,以及基本重写
@NoArgsConstructor//无参构造
@AllArgsConstructor//全参构造
public class SportSearchVO {
    private String keyword;
    private String gender;
    private String gradeId;
    private String classId;
}
