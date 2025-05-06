package com.fiveup.core.mainpage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fiveup.core.mainpage.domain.po.WebUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MainPageMapper extends BaseMapper<WebUser> {

    @Select("select school_name from basic_school where id = #{id}")
    String getSchoolInfo(Integer id);

    @Select("select identity_info from basic_user_identity where identity_id = #{id}")
    String getIdentityInfo(Integer id);
}
