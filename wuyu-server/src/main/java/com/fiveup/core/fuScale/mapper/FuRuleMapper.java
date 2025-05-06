package com.fiveup.core.fuScale.mapper;

import com.fiveup.core.fuScale.model.Detail;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FuRuleMapper {

    //
    @Insert("insert into " +
            "fu_detail_content(name, grade, level, score_min, score_max, data_min, data_max, isDataType, scale_id, item_id) " +
            "value(#{name}, #{grade}, #{level}, #{scoreMin}, #{scoreMax}, #{dataMin}, #{dataMax}, #{isDataType}, #{scaleId}, #{itemId})")
    int insertDetail(Detail detail);

}
