package com.fiveup.core.analyze.repository;


import com.fiveup.core.analyze.entity.Ana_updata_set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UpdataSetRepository extends JpaRepository<Ana_updata_set, Integer> { //<实体类，主键类型>
}
