package com.fiveup.core.noticeBooklet.service.impl;

import com.fiveup.core.noticeBooklet.domain.NoticeBooklet;
import com.fiveup.core.noticeBooklet.mapper.ScoreMapper;
import com.fiveup.core.noticeBooklet.service.NoticeBookletService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 通知册服务层
 *
 * @author huangxingcan
 * @date 2025/6/7 17:18
 */
@Service
public class NoticeBookletServiceImpl implements NoticeBookletService {

    @Resource
    private ScoreMapper scoreMapper;

    /**
     * 获取通知册内容
     *
     * @param studentId 学号
     * @param classId   班级id
     * @param gradeId   年级id
     * @return 通知册
     */
    @Override
    public List<NoticeBooklet> getNoticeBooklet(Integer studentId, Integer classId, Integer gradeId) {
        List<NoticeBooklet> noticeBookletList = scoreMapper.getNoticeBooklet(studentId, classId, gradeId);
        // 获取数据
        for (NoticeBooklet noticeBooklet : noticeBookletList) {
            // 获取评语
            // TODO：大模型获取
            noticeBooklet.setRemark("评语：暂无");
            // 获取建议
            // TODO：大模型获取
            noticeBooklet.setSuggest("建议：暂无");
            // 获取假期要求
            noticeBooklet.setHoliday("假期要求：暂无");
        }
        return noticeBookletList;
    }
}
