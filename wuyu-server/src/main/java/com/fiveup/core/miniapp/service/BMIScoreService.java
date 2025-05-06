package com.fiveup.core.miniapp.service;


import com.fiveup.core.miniapp.model.ScoreSport;
import com.fiveup.core.miniapp.model.StuScore;

import java.util.List;

/**
 * @author shilin
 * @date 2022/10/3
 */
public interface BMIScoreService {

    Boolean isRecordExist(Long studentNum);

    Boolean insertSportScore(ScoreSport scoreSport);

    Boolean updateSportScore(ScoreSport scoreSport);

    List<StuScore> getBMIScoreList();
}
