package com.fiveup.core.notice.enetity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @ author rdx
 * @ describe :
 * @ date  2025/6/11
 **/
@Data
public class NoticeEntity {
    private String id;
    private String theme;
    private String content;
    private LocalDateTime releaseTime;
    private String identityId;
    private Boolean isImportant;
    private Long createdBy;
}