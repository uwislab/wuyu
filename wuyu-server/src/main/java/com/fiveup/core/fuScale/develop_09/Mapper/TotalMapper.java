package com.fiveup.core.fuScale.develop_09.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fiveup.core.fuScale.model.ScaleInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TotalMapper extends BaseMapper<ScaleInfo> {

    @Select("select *,id scale_id from fu_scale_content")
    Page<ScaleInfo> selectWithPagination();


//    select *,id scale_id from fu_scale_content
//select teacher_name from basic_teacher where id = #{id}
//select count(1) from fu_item_content where scale_id = #{id}
}
