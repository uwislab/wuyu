package com.fiveup.core.award.service;

import com.fiveup.core.award.entity.Award;

import java.util.List;
import java.util.Optional;

public interface AwardService {
    List<Award> getAllAwards();
    Optional<Award> getAwardById(Long id);
    Award createAward(Award award);
    Award updateAward(Long id, Award awardDetails);
    void deleteAward(Long id);
    List<Award> searchAwardsByName(String name);
}