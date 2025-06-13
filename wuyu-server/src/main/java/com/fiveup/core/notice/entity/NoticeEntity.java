package com.fiveup.core.notice.entity;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * @ClassName NoticeEntity
 * @Description weiweijian_projects
 * @Author tangshangpeng
 * @Date 2025/6/13 9:47
 * @Vision 1.0
 **/
@Data
@Getter
public class NoticeEntity {
    private String id;
    private String theme;
    private String content;
    private LocalDateTime releaseTime;
    private String identityId;
    private Boolean isImportant;
    private Long createdBy;
}
