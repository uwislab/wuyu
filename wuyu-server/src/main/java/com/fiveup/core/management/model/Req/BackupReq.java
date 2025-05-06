package com.fiveup.core.management.model.Req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 陈玉庆
 * @date 2024/11/25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BackupReq {
    private String remoteIP;
    private String username;
    private String password;
    private Integer localBackup;
}
