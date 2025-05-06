package com.fiveup.core.analyze.repository;


import com.fiveup.core.analyze.entity.Ana_subject_relation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Ana_subject_relation, Integer> { //<实体类，主键类型>
}
