package com.fiveup.core.miniapp.service.impl;

import com.fiveup.core.miniapp.mapper.RSScoreMapper;
import com.fiveup.core.miniapp.model.ScoreSport;
import com.fiveup.core.miniapp.model.StuScore;
import com.fiveup.core.miniapp.service.RSScoreService;
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
public class RSScoreServiceImpl implements RSScoreService {

    @Resource
    private RSScoreMapper rsScoreMapper;

    @Override
    public Boolean isRecordExist(Long studentNum) {
        return rsScoreMapper.isRecordExist(studentNum) != null;
    }

    @Override
    public Boolean insertSportScore(ScoreSport scoreSport) {
        return rsScoreMapper.insertSportScore(scoreSport) >= 1;
    }

    @Override
    public Boolean updateSportScore(ScoreSport scoreSport) {
        return rsScoreMapper.updateSportScore(scoreSport) >=0;
    }

    @Override
    public List<StuScore> getRSScoreList() { return rsScoreMapper.getRSScoreList(); }
}
