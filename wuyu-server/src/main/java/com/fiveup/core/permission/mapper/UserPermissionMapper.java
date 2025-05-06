package com.fiveup.core.permission.mapper;


import com.fiveup.core.permission.entity.UserPermissionInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface UserPermissionMapper{

    List<UserPermissionInfo> findByCondition(String userName, Integer identity, String realName);

    @Select("select * from basic_web_user")
    List<UserPermissionInfo> selectAll();

    @Update("UPDATE basic_web_user SET " +
            "identity = #{identity}, " +
            "password = #{password} " +
            "WHERE id = #{id}")
    void edit(UserPermissionInfo userPermissionInfo);

    int deleteByIds(@Param("ids") String[] ids);

}
