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
     * @param studentNum 学号
     * @param classId 班级id
     * @param gradeId 年级id
     * @param schoolId 学校id
     * @return 通知册
     */
    List<NoticeBooklet> getNoticeBooklet(Long studentNum, Integer classId, Integer gradeId, Long schoolId);
}
