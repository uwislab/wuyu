package com.fiveup.core.management.service;

import com.fiveup.core.management.pojo.SportScore;
import com.fiveup.core.management.pojo.SportSearchVO;

import java.util.List;


public interface SportScoreService {
    /**
     * 获取所有体育成绩
     * @return 体育成绩集合
     */
    List<SportScore> getSportScore();

    /**
     * 搜索体育成绩
     * @return 体育成绩集合
     */
    List<SportScore> findSportScoreForSearch(SportSearchVO sportSearchVO);

    /**
     * 编辑体育成绩
     * @param score 体育成绩
     * @return 错误信息
     */
    List<String> editBmiSportScore(SportScore score);

    /**
     * 删除体育成绩
     * @param score 成绩
     * @return 错误信息
     */
    List<String> removeSportScore(SportScore score);

    /**
     * 添加体育成绩
     * @param score 成绩
     * @return 错误信息
     */
    List<String> addSportScore(SportScore score);

    /**
     * 批量删除体育成绩
     * @param ids 学号集合
     * @return 错误信息
     */
    List<String> deleteBatchSportScore(List<String> ids);
}
