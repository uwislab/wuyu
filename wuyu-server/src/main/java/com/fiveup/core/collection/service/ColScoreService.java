package com.fiveup.core.collection.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fiveup.core.collection.model.ColScore;

import java.util.List;

/**
 * @Copyright (C), 2015-2021, XXX有限公司
 * @ClassName: ColScoreService
 * @Author: 作者姓名(一般是写姓名的拼音)
 * @E-mail: 邮箱
 * @Date: 2021/4/21 9:00
 * @Version: V1.0
 * @Description: 对该类的描述
 */
public interface ColScoreService extends IService<ColScore> {

    /**
     * 获得学生成绩
     * @return com.elk.fiveup.core.collection.model.ColScore
     * @throws
     */
    ColScore getScore(Long id);

    /**
     * 添加学生成绩
     * @return
     * @throws
     */
    int addScore(ColScore colScore);

    /**
     * 修改学生成绩
     * @return
     * @throws
     */
    int updateScore(ColScore colScore);

    /**
     * 删除学生成绩
     * @return
     * @throws
     */
    boolean deleteScore(Long id);

    /**
     * 批量删除学生成绩
     * @param ids
     * @return
     */
    boolean deleteScore(List<Long> ids);
}
