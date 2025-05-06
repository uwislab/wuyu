package com.fiveup.core.cultivation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fiveup.core.cultivation.entity.Minutes;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Harvi
 */
@Component
@Mapper
public interface MinutesMapper extends BaseMapper<Minutes> {
       /**
        * TODO
        * @param id TODO
        * @return TODO
        */
       Minutes getOne(Integer id);

       /**
        * TODO
        * @param o TODO
        * @return TODO
        */
       List<Minutes> getAll(Minutes o);
}
