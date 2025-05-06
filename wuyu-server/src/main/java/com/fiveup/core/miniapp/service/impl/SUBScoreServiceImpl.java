package com.fiveup.core.miniapp.service.impl;

import com.fiveup.core.miniapp.mapper.SUBScoreMapper;
import com.fiveup.core.miniapp.model.ScoreSport;
import com.fiveup.core.miniapp.model.StuScore;
import com.fiveup.core.miniapp.service.SUBScoreService;
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
public class SUBScoreServiceImpl implements SUBScoreService {

    @Resource
    private SUBScoreMapper subScoreMapper;

    @Override
    public Boolean isRecordExist(Long studentNum) {
        return subScoreMapper.isRecordExist(studentNum) != null;
    }

    @Override
    public Boolean insertSportScore(ScoreSport scoreSport) {
        return subScoreMapper.insertSportScore(scoreSport) >= 1;
    }

    @Override
    public Boolean updateSportScore(ScoreSport scoreSport) {
        return subScoreMapper.updateSportScore(scoreSport) >=0;
    }

    @Override
    public List<StuScore> getSUBScoreList() { return subScoreMapper.getSUBScoreList(); }
}
