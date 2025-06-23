package com.fiveup.core.fuScale.mapper;

import com.fiveup.core.fuScale.model.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Mapper
@Repository
public interface FuScaleHisMapper {

    @Select("select teacher_name from basic_teacher where id = #{id}")
    String getCreatorName(Integer id);

    // 新增评价量表表头并返回自增id
    @Insert("insert into fu_scale_content_his(scale_id, title, create_date, state, creator_id, domain, grade, update_date, update_id, update_batch) " +
            "value(#{scaleId}, #{title}, #{createDate}, #{state}, #{creatorId}, #{domain}, #{grade}, #{updateDate}, #{updateId}, #{updateBatch})")
//    @Options(useGeneratedKeys = true, keyProperty = "scaleId")
    int insertFuScale(ScaleHisInfo scaleInfo);

    @Select("select * from fu_scale_content_his where state = #{stateId}")
    List<ScaleHisInfo> getScaleByState(Integer stateId);

    @Select("select * from fu_scale_content_his where scale_id = #{id}")
    List<ScaleHisInfo> getScaleHisListById(Integer id);

}
