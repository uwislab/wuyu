package com.fiveup.core.analyze.repository;


import com.fiveup.core.analyze.entity.Ana_center_data;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CenterDataRepository extends JpaRepository<Ana_center_data, Integer> { //<实体类，主键类型>
}
