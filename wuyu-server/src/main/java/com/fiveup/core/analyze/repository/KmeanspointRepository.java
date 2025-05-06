package com.fiveup.core.analyze.repository;

import com.fiveup.core.analyze.entity.Ana_kmeanspoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KmeanspointRepository  extends JpaRepository<Ana_kmeanspoint, Integer> { //<实体类，主键类型>
}

