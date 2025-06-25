package com.fiveup.core.notice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @ClassName NoticeReadRecord
 * @Description weiweijian_projects
 * @Author tangshangpeng
 * @Date 2025/6/10 15:41
 * @Vision 1.0
 **/
@Data
@TableName("notice_read_record")
public class NoticeReadRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long noticeId;
    private int userId;
    private LocalDateTime readTime;
}
