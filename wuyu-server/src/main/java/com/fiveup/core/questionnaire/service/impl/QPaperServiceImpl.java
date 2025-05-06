package com.fiveup.core.questionnaire.service.impl;

import java.util.Date;
import java.util.List;

import com.fiveup.core.questionnaire.mapper.QQuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fiveup.core.questionnaire.mapper.QPaperMapper;
import com.fiveup.core.questionnaire.domain.QPaper;
import com.fiveup.core.questionnaire.service.IQPaperService;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 问卷信息Service业务层处理
 * 
 * @author
 * @date
 */
@Service
public class QPaperServiceImpl implements IQPaperService 
{
    @Autowired
    private QPaperMapper qPaperMapper;

    @Resource
    private QQuestionMapper qQuestionMapper;

    /**
     * 查询问卷信息
     * 
     * @param paperId 问卷信息主键
     * @return 问卷信息
     */
    @Override
    public QPaper selectQPaperByPaperId(Long paperId)
    {
        return qPaperMapper.selectQPaperByPaperId(paperId);
    }

    /**
     * 查询问卷信息列表
     * 
     * @param qPaper 问卷信息
     * @return 问卷信息
     */
    @Override
    public List<QPaper> selectQPaperList(QPaper qPaper)
    {
        return qPaperMapper.selectQPaperList(qPaper);
    }

    @Override
    public Integer selectCount(QPaper qPaper) {
        return qPaperMapper.selectCount(qPaper);
    }

    //检查数据有效性
    public boolean IsValidQPaper(QPaper qPaper) {
        if (qPaper.getPaperTitle().isEmpty()) {
            return false;
        }
        if (qPaper.getPaperType().isEmpty()) {
            return false;
        }
        if (qPaper.getDescription().isEmpty()) {
            return false;
        }
        if (qPaper.getStartTime() == null) {
            return false;
        }
        if (qPaper.getEndTime() == null) {
            return false;
        }
        if (qPaper.getStartTime().getTime() > qPaper.getEndTime().getTime()) {
            return false;
        }
        return true;
    }
    /**
     * 新增问卷信息
     * 
     * @param qPaper 问卷信息
     * @return 结果
     */
    @Override
    @Transactional(propagation=Propagation.NESTED,isolation= Isolation.READ_COMMITTED)
    public int insertQPaper(QPaper qPaper) {
        if (!this.IsValidQPaper(qPaper)) {
            return -1;
        }
        int i = qPaperMapper.insertQPaper(qPaper);
        return i;
    }

    /**
     * 修改问卷信息
     * 
     * @param qPaper 问卷信息
     * @return 结果
     */
    @Override
    public int updateQPaper(QPaper qPaper) {
        if (!this.IsValidQPaper(qPaper)) {
            return -1;
        }
        String releaseStatus = "1";
        String endStatus = "2";
        // 获取当前时间
        Date currentTime = new Date();

        // 检查问卷状态并更新开始时间或结束时间
        if (releaseStatus.equals(qPaper.getPaperStatus())) {
            qPaper.setStartTime(currentTime); // 设置开始时间为当前时间
        } else if (endStatus.equals(qPaper.getPaperStatus())) {
            qPaper.setEndTime(currentTime); // 设置结束时间为当前时间
        }

        // 查询问卷下的问题数量
        int questionCount = qQuestionMapper.selcetListByPaperId(qPaper.getPaperId());
        // 获取数据库中原来的问卷状态
        //String existingPaperStatus = qPaperMapper.selectQPaperByPaperId(qPaper.getPaperId()).getPaperStatus();

        // 校验逻辑：如果没有问题且标题为空，则返回失败
        if (questionCount <= 0 && (qPaper.getPaperStatus().equals("1")||qPaper.getPaperStatus()==null)) {
            return -1;
        }

        System.out.println(qPaperMapper.updateQPaper(qPaper));
        // 更新问卷
        return qPaperMapper.updateQPaper(qPaper);
    }

    /**
     * 批量删除问卷信息
     * 
     * @param paperIds 需要删除的问卷信息主键
     * @return 结果
     */
    @Override
    public int deleteQPaperByPaperIds(Long[] paperIds)
    {
        return qPaperMapper.deleteQPaperByPaperIds(paperIds);
    }

    /**
     * 删除问卷信息信息
     * 
     * @param paperId 问卷信息主键
     * @return 结果
     */
    @Override
    public int deleteQPaperByPaperId(Long paperId)
    {
        return qPaperMapper.deleteQPaperByPaperId(paperId);
    }

    @Override
    public Long[] selectQuestId(Long paperId){return qPaperMapper.selectQuestId(paperId);}
}
