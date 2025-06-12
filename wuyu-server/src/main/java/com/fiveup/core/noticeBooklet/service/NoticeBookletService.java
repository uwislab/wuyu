package com.fiveup.core.noticeBooklet.service;

import com.fiveup.core.noticeBooklet.domain.NoticeBooklet;

import java.util.List;

/**
 * 通知册服务层
 *
 * @author huangxingcan
 * @date 2025/6/7 17:18
 */
public interface NoticeBookletService {

    /**
     * 获取通知册内容
     * @param studentId 学号
     * @param classId 班级id
     * @param gradeId 年级id
     * @return 通知册
     */
    List<NoticeBooklet> getNoticeBooklet(Integer studentId, Integer classId, Integer gradeId);
}
