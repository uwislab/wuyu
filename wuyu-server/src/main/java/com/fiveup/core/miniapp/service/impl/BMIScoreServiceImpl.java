package com.fiveup.core.miniapp.service.impl;

import com.fiveup.core.miniapp.mapper.BMIScoreMapper;
import com.fiveup.core.miniapp.model.ScoreSport;
import com.fiveup.core.miniapp.model.StuScore;
import com.fiveup.core.miniapp.service.BMIScoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author shilin
 * @date 2022/10/3
 */
@Slf4j
@Service
public class BMIScoreServiceImpl implements BMIScoreService {

    @Resource
    private BMIScoreMapper bmiScoreMapper;

    @Override
    public Boolean isRecordExist(Long studentNum) {
        return bmiScoreMapper.isRecordExist(studentNum) != null;
    }

    @Override
    public Boolean insertSportScore(ScoreSport scoreSport) {
        return bmiScoreMapper.insertSportScore(scoreSport) >= 1;
    }

    @Override
    public Boolean updateSportScore(ScoreSport scoreSport) {
        return bmiScoreMapper.updateSportScore(scoreSport) >=0;
    }

    @Override
    public List<StuScore> getBMIScoreList() { return bmiScoreMapper.getBMIScoreList(); }
}
