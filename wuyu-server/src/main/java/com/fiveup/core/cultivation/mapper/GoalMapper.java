package com.fiveup.core.cultivation.mapper;
import com.fiveup.core.cultivation.entity.Indicator;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fiveup.core.cultivation.entity.Goal;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Harvi
 */
@Mapper
@Component
public interface GoalMapper extends BaseMapper<Goal> {
       /**
        * TODO
        * @param id TODO
        * @return TODO
        */
       Goal getById(Integer id);

       /**
        * TODO
        * @param goal TODO
        * @return TODO
        */
       List<Goal> getList(Goal goal);

       

       /**
        *
        * @param goal
        * @return
        */
       List<Goal> getListByTn(Goal goal);

       /**
        *
        * @param goal 目标
        * @return {@link List}<{@link Goal}>
        */
       List<Goal> getAllByEditorTids(Goal goal);

       Indicator selectIndicator(Integer id);

       void updateIndicator(Indicator indicator);
}
