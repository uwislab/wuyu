package com.fiveup.core.award.mapper;
import com.fiveup.core.award.entity.Award;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AwardMapper extends JpaRepository<Award, Long> {
    List<Award> findByNameContaining(String name); // 模糊查询
}