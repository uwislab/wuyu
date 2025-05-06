package com.fiveup.core.management.model.Req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xx
 * @date 2024/11/25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TiyuScoreReq {
    private Integer pageNum;
    private Integer pageSize;
    private Integer gradeId;
    private Integer classId;

}
