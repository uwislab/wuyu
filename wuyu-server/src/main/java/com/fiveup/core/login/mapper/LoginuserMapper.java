package com.fiveup.core.login.mapper;

import com.fiveup.core.login.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LoginuserMapper {

    @Select("SELECT * FROM basic_web_user WHERE username = #{username}")
    User findByUsername(String username);
}
