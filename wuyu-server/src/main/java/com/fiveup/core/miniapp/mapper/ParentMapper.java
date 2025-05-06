package com.fiveup.core.miniapp.mapper;

import com.fiveup.core.miniapp.model.Parent;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ParentMapper {

    @Insert("insert into basic_parent(username,password) values(#{username},#{password})")
    int addParent(Parent ParentVO);

    @Select("select * from basic_parent where username=#{username} and password=#{password}")
    Parent getParent(Parent Parent);
}
