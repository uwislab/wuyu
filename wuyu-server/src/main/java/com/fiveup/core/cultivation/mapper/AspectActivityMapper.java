package com.fiveup.core.cultivation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fiveup.core.cultivation.entity.AspectActivity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Harvi
 */
@Mapper
@Component
public interface AspectActivityMapper extends BaseMapper<AspectActivity> {
    /**
     * TODO
     * @param activity TODO
     * @return TODO
     */
    List<AspectActivity> getListByAid(AspectActivity activity);
}
