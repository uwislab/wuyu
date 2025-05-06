package com.fiveup.core.permission.service;


import com.fiveup.core.courseScore.entity.ResPage;
import com.fiveup.core.permission.entity.UserPermissionInfo;

import java.util.List;

public interface UserPermissionService {

    //查询所有用户
    List<UserPermissionInfo> getAll();

    //按条件查询
    ResPage<List<UserPermissionInfo>> search(String userName, Integer identity, String realName, Integer page, Integer pageSize);

    //修改信息
    void update(UserPermissionInfo userPermissionInfo);

    //删除用户
    boolean deleteByIds(String[] ids);
}
