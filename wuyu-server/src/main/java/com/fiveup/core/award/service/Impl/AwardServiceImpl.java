package com.fiveup.core.award.service.Impl;

import com.fiveup.core.award.entity.Award;
import com.fiveup.core.award.mapper.AwardMapper;
import com.fiveup.core.award.service.AwardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AwardServiceImpl implements AwardService {

    @Autowired
    private AwardMapper awardMapper;

    @Override
    public List<Award> getAllAwards() {
        return awardMapper.findAll();
    }

    @Override
    public Optional<Award> getAwardById(Long id) {
        return awardMapper.findById(id);
    }

    @Override
    public Award createAward(Award award) {
        return awardMapper.save(award);
    }

    @Override
    public Award updateAward(Long id, Award awardDetails) {
        Award award = awardMapper.findById(id).get(); // 可以添加自定义异常
        award.setDate(awardDetails.getDate());
        award.setName(awardDetails.getName());
        return awardMapper.save(award);
    }

    @Override
    public void deleteAward(Long id) {
        awardMapper.deleteById(id);
    }

    @Override
    public List<Award> searchAwardsByName(String name) {
        return awardMapper.findByNameContaining(name);
    }


}