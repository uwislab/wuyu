package com.fiveup.core.miniapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Date: 2024/5/22
 * Author: zfy15
 * Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParentReq {
    private Integer studentNum;
    private String realName;
    private Integer pageNum;
    private Integer pageSize;
}
