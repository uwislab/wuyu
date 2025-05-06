package com.fiveup.core.miniapp.model;

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
public class StuScore {
    private String studentNum;
    private String studentName;
    private Integer score;
}
