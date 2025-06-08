package com.fiveup.core.noticeBooklet.service.impl;

import com.fiveup.core.diagnose.bean.student_score;
import com.fiveup.core.diagnose.service.studentscoreService;
import com.fiveup.core.management.mapper.StuMapper;
import com.fiveup.core.management.model.DTO.StuDTO;
import com.fiveup.core.noticeBooklet.domain.NoticeBooklet;
import com.fiveup.core.noticeBooklet.service.CommentGenerationService;
import com.fiveup.core.noticeBooklet.service.NoticeBookletService;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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
    private studentscoreService stService;
    @Resource
    private StuMapper stuMapper;
    @Resource
    private CommentGenerationService commentGenerationService;

    /**
     * 获取通知册内容
     *
     * @param studentNum 学号
     * @param classId   班级id
     * @param gradeId   年级id
     * @param schoolId   学校id
     * @return 通知册
     */
    @Override
    public List<NoticeBooklet> getNoticeBooklet(Long studentNum, Integer classId, Integer gradeId, Long schoolId) {
        List<NoticeBooklet> noticeBookletList = new ArrayList<>();
        // 获取所有学生信息
        List<StuDTO> studentIdList = new ArrayList<>();
        if (studentNum != null) {
            studentIdList.add(stuMapper.getStuInfoByStudentNum(studentNum));
        } else {
            studentIdList = stuMapper.getStudentListByConditions(null, null, null, classId, gradeId, schoolId);
        }
        // 获取数据
        for (StuDTO stuDTO : studentIdList) {
            // 获取学生成绩
            List<student_score> studentScoreList = stService.SelectScoreByKey(stuDTO.getStudentName(), stuDTO.getStudentId());
            NoticeBooklet noticeBooklet = new NoticeBooklet();
            noticeBooklet.setStudent(stuDTO);
            noticeBooklet.setStudentScoreList(studentScoreList);
            // 获取评语
            try {
                String comment = commentGenerationService.generateCommentForStudent(stuDTO.getStudentName(),stuDTO.getStudentId());
                noticeBooklet.setRemark(comment);
            } catch (ApiException | NoApiKeyException | InputRequiredException e) {
                noticeBooklet.setRemark("生成评语失败：" + e.getMessage());
            }

            // 获取建议
            // TODO：大模型获取
            noticeBooklet.setSuggest("暂无建议");

            // 获取假期要求
            noticeBooklet.setHoliday("假期要求：暂无");
            noticeBookletList.add(noticeBooklet);
        }
        return noticeBookletList;
    }
}