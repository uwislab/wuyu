package com.fiveup.core.notice.vo;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class NoticeVO {
    private Long id;
    private String theme;
    private String content;
    private LocalDateTime releaseTime;
    private Boolean isImportant;
    private Boolean isRead;  // 是否已读
}
