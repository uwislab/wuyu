package com.fiveup.core.miniapp.service.impl;

import com.fiveup.core.miniapp.mapper.FVCScoreMapper;
import com.fiveup.core.miniapp.model.ScoreSport;
import com.fiveup.core.miniapp.model.StuScore;
import com.fiveup.core.miniapp.service.FVCScoreService;
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
public class FVCScoreServiceImpl implements FVCScoreService {

    @Resource
    private FVCScoreMapper fvcScoreMapper;

    @Override
    public Boolean isRecordExist(Long studentNum) {
        return fvcScoreMapper.isRecordExist(studentNum) != null;
    }

    @Override
    public Boolean insertSportScore(ScoreSport scoreSport) {
        return fvcScoreMapper.insertSportScore(scoreSport) >= 1;
    }

    @Override
    public Boolean updateSportScore(ScoreSport scoreSport) {
        return fvcScoreMapper.updateSportScore(scoreSport) >=0;
    }

    @Override
    public List<StuScore> getFVCScoreList() { return fvcScoreMapper.getFVCScoreList(); }
}
