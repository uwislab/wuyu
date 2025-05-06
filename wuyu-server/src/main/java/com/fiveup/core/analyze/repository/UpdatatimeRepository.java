package com.fiveup.core.analyze.repository;


import com.fiveup.core.analyze.entity.Ana_updata_time;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UpdatatimeRepository extends JpaRepository<Ana_updata_time, Integer> { //<实体类，主键类型>
}
