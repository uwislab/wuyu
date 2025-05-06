package com.fiveup.core.miniapp.service.impl;

import com.fiveup.core.miniapp.mapper.SARScoreMapper;
import com.fiveup.core.miniapp.model.ScoreSport;
import com.fiveup.core.miniapp.model.StuScore;
import com.fiveup.core.miniapp.service.SARScoreService;
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
public class SARScoreServiceImpl implements SARScoreService {

    @Resource
    private SARScoreMapper sarScoreMapper;

    @Override
    public Boolean isRecordExist(Long studentNum) {
        return sarScoreMapper.isRecordExist(studentNum) != null;
    }

    @Override
    public Boolean insertSportScore(ScoreSport scoreSport) {
        return sarScoreMapper.insertSportScore(scoreSport) >= 1;
    }

    @Override
    public Boolean updateSportScore(ScoreSport scoreSport) {
        return sarScoreMapper.updateSportScore(scoreSport) >=0;
    }

    @Override
    public List<StuScore> getSARScoreList() { return sarScoreMapper.getSARScoreList(); }
}
