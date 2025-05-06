package com.fiveup.core.miniapp.service.impl;

import com.fiveup.core.miniapp.mapper.Dash50ScoreMapper;
import com.fiveup.core.miniapp.model.ScoreSport;
import com.fiveup.core.miniapp.model.StuScore;
import com.fiveup.core.miniapp.service.Dash50ScoreService;
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
public class Dash50ScoreServiceImpl implements Dash50ScoreService {

    @Resource
    private Dash50ScoreMapper dash50ScoreMapper;

    @Override
    public Boolean isRecordExist(Long studentNum) {
        return dash50ScoreMapper.isRecordExist(studentNum) != null;
    }

    @Override
    public Boolean insertSportScore(ScoreSport scoreSport) {
        return dash50ScoreMapper.insertSportScore(scoreSport) >= 1;
    }

    @Override
    public Boolean updateSportScore(ScoreSport scoreSport) {
        return dash50ScoreMapper.updateSportScore(scoreSport) >=0;
    }

    @Override
    public List<StuScore> getDash50ScoreList() { return dash50ScoreMapper.getDash50ScoreList(); }
}
