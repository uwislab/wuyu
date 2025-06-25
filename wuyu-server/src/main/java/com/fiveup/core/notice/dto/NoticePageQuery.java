package com.fiveup.core.notice.dto;

import lombok.Data;


@Data
public class NoticePageQuery {
    private int userId;
    private String keyword; // 搜索关键词
    private Integer pageNum = 1; // 当前页码，默认为第一页
    private Integer pageSize = 10; // 每页大小，默认为10条
    private String identityId; // 新增：用户身份ID
}
