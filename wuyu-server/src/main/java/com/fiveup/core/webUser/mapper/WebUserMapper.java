package com.fiveup.core.webUser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fiveup.core.webUser.entity.webUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WebUserMapper extends BaseMapper<webUser> {
    // 基于 MyBatis-Plus，无需额外代码即可实现常见的增删改查
}
