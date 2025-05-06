package com.fiveup.core.collection.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fiveup.core.collection.mapper.ColScoreMapper;
import com.fiveup.core.collection.model.ColScore;
import com.fiveup.core.collection.service.ColScoreService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Copyright (C), 2015-2021, XXX有限公司
 * @ClassName: ColScoreServiceImpl
 * @Author: 作者姓名(一般是写姓名的拼音)
 * @E-mail: 邮箱
 * @Date: 2021/4/21 8:59
 * @Version: V1.0
 * @Description: 对该类的描述
 */
@Service
public class ColScoreServiceImpl extends ServiceImpl<ColScoreMapper, ColScore> implements ColScoreService {


    @Override
    public ColScore getScore(Long id){
        return getById(id);
    }

    @Override
    public int addScore(ColScore colScore){
        //ColScore colScore1 = new ColScore();
        //BeanUtils.copyProperties(colScore,colScore1);
        System.out.println("========================"+colScore.toString());
        return baseMapper.insert(colScore);
    }

    @Override
    public int updateScore(ColScore colScore){
        //ColScore colScore1 =new ColScore();
        //BeanUtils.copyProperties(colScore,colScore1);
        //colScore1.setId(id);

        System.out.println(colScore.toString());
        return baseMapper.updateById(colScore);
    }

    @Override
    public boolean deleteScore(Long id){
        return removeById(id);
    }

    @Override
    public boolean deleteScore(List<Long> ids){
        return removeByIds(ids);
    }
}
