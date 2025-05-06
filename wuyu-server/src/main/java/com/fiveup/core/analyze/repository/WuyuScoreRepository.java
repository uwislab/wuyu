package com.fiveup.core.analyze.repository;


import com.fiveup.core.analyze.entity.Ana_wuyu_score;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WuyuScoreRepository extends JpaRepository<Ana_wuyu_score, Integer>{ //<实体类，主键类型>
}

