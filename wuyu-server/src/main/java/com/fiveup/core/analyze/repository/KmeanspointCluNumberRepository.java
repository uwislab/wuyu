package com.fiveup.core.analyze.repository;

import com.fiveup.core.analyze.entity.Ana_kmeanspoint_clunumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KmeanspointCluNumberRepository extends JpaRepository<Ana_kmeanspoint_clunumber, Integer> { //<实体类，主键类型>
}

