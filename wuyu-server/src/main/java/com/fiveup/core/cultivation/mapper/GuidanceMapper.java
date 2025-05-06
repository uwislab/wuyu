package com.fiveup.core.cultivation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fiveup.core.cultivation.entity.Guidance;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Harvi
 */
@Component
@Mapper
public interface GuidanceMapper extends BaseMapper<Guidance> {
       /**
        * todo
        * @param o todo
        * @return todo
        */
       List<Guidance> getAll(Guidance o);
}