package com.fiveup.core.analyze.repository;


import com.fiveup.core.analyze.entity.Ana_wuyu_flunk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WuyuFlunkRepository extends JpaRepository<Ana_wuyu_flunk, Integer> { //<实体类，主键类型>
}
