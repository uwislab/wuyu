package com.fiveup.core.miniapp.service.impl;

import com.fiveup.core.miniapp.mapper.OAB50x8ScoreMapper;
import com.fiveup.core.miniapp.model.ScoreSport;
import com.fiveup.core.miniapp.model.StuScore;
import com.fiveup.core.miniapp.service.OAB50x8ScoreService;
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
public class OAB50x8ScoreServiceImpl implements OAB50x8ScoreService {

    @Resource
    private OAB50x8ScoreMapper oab50x8ScoreMapper;

    @Override
    public Boolean isRecordExist(Long studentNum) {
        return oab50x8ScoreMapper.isRecordExist(studentNum) != null;
    }

    @Override
    public Boolean insertSportScore(ScoreSport scoreSport) {
        return oab50x8ScoreMapper.insertSportScore(scoreSport) >= 1;
    }

    @Override
    public Boolean updateSportScore(ScoreSport scoreSport) {
        return oab50x8ScoreMapper.updateSportScore(scoreSport) >=0;
    }

    @Override
    public List<StuScore> getOAB50x8ScoreList() { return oab50x8ScoreMapper.getOAB50x8ScoreList(); }
}
