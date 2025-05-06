package com.fiveup.core.questionnaire.service;

import com.fiveup.core.questionnaire.vo.PaperVO;
import com.fiveup.core.questionnaire.vo.ResponseVO;

/**
 * @Copyright (C), 2015-2021, XXX有限公司
 * @ClassName: PaperService
 * @Author: 作者姓名(一般是写姓名的拼音)
 * @E-mail: 邮箱
 * @Date: 2021/4/26 12:28
 * @Version: V1.0
 * @Description: 对该类的描述
 */
public interface PaperService {

    ResponseVO addPaper(PaperVO paperVO);

    ResponseVO updatePaper(PaperVO paperVO);

    ResponseVO deletePaper(int paperId);

    ResponseVO getUserPapers(int userId);

    ResponseVO checkPaper(int paperId);

    ResponseVO reviewPaper(int paperId);

}
