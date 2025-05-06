package com.fiveup.core.cultivation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fiveup.core.cultivation.entity.Meeting;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Harvi
 */
@Component
@Mapper
public interface MeetingMapper extends BaseMapper<Meeting> {
       /**
        * TODO
        * @param id TODO
        * @return TODO
        */
       Meeting getOne(Integer id);

       /**
        * TODO
        * @param o TODO
        * @return TODO
        */
       List<Meeting> getAll(Meeting o);
}