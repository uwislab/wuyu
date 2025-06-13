package com.fiveup.core.notice.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName NoticeIdentityEntity
 * @Description weiweijian_projects
 * @Author tangshangpeng
 * @Date 2025/6/13 9:47
 * @Vision 1.0
 **/
@Data
@TableName("notice_identity_record")
public class NoticeIdentityEntity {
    private String noticeId;// 公告ID，关联 notice 表

    private String identityId;// 身份ID

}
