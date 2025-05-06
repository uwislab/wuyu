package com.fiveup.core.analyze.repository;


import com.fiveup.core.analyze.entity.Ana_cluster_number;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClusterNumberRepository extends JpaRepository<Ana_cluster_number, Integer> { //<实体类，主键类型>
}
