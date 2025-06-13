package com.fiveup.core.notice.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @ClassName NoticeVO
 * @Description weiweijian_projects
 * @Author tangshangpeng
 * @Date 2025/6/10 15:42
 * @Vision 1.0
 **/
@Data
public class NoticeVO {
    private Long id;
    private String theme;
    private String content;
    private LocalDateTime releaseTime;
    private Boolean isImportant;
    private Boolean isRead;  // 是否已读
}
