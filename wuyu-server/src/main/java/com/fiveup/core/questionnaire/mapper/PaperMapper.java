package com.fiveup.core.questionnaire.mapper;

import com.fiveup.core.questionnaire.enums.PaperStatus;
import com.fiveup.core.questionnaire.po.Paper;
import com.fiveup.core.questionnaire.vo.PaperVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Copyright (C), 2015-2021, XXX有限公司
 * @ClassName: PaperMapper
 * @Author: 作者姓名(一般是写姓名的拼音)
 * @E-mail: 邮箱
 * @Date: 2021/4/26 12:26
 * @Version: V1.0
 * @Description: 对该类的描述
 */
@Mapper
@Repository
public interface PaperMapper {

    int addPaper(PaperVO paperVO);

    void updatePaper(PaperVO paperVO);

    PaperVO selectByPaperId(int paperId);

    void deletePaper(int paperId);

    List<PaperVO> getUserPapers(int userId);

    List<PaperVO> getTimePapers();

    void changeStatus(@Param("paperStatus") String paperStatus, @Param("id") int id);

    List<Paper>selectTimeOut();
}
