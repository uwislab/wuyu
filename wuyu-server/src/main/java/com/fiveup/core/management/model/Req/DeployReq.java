package com.fiveup.core.management.model.Req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 尔宣赫
 * @date 2022/4/30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeployReq {
    private String remoteIP;
    private String username;
    private String password;
}
