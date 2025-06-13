package com.fiveup.core.notice.dto;

import lombok.Data;

/**
 * @ClassName NoticePageQuery
 * @Description weiweijian_projects
 * @Author tangshangpeng
 * @Date 2025/6/12 22:41
 * @Vision 1.0
 **/
@Data
public class NoticePageQuery {
    private int userId;
    private String keyword; // 搜索关键词
    private Integer pageNum = 1; // 当前页码，默认为第一页
    private Integer pageSize = 10; // 每页大小，默认为10条
    private String identityId; // 新增：用户身份ID
}
