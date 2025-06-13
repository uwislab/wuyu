package com.fiveup.core.notice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @ClassName Notice
 * @Description weiweijian_projects
 * @Author tangshangpeng
 * @Date 2025/6/10 15:40
 * @Vision 1.0
 **/
@Data
@TableName("notice")
public class Notice {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String theme;
    private String content;
    private LocalDateTime releaseTime;
    private Boolean isImportant;
    private Long createdBy;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;


}
