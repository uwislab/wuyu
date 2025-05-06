package com.fiveup.core.permission.service.impl;

import com.fiveup.core.courseScore.entity.ResPage;
import com.fiveup.core.permission.entity.UserPermissionInfo;
import com.fiveup.core.permission.mapper.UserPermissionMapper;
import com.fiveup.core.permission.service.UserPermissionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserPermissionServiceImpl implements UserPermissionService {

    @Autowired
    private UserPermissionMapper userPermissionMapper;

    @Override
    public List<UserPermissionInfo> getAll() {
        return userPermissionMapper.selectAll();
    }

    @Override
    public ResPage<List<UserPermissionInfo>> search(String userName, Integer identity, String realName, Integer page, Integer pageSize) {
        // 设置分页信息
        PageHelper.startPage(page, pageSize);
        // 执行sql
        List<UserPermissionInfo> UserPermissionInfoList = userPermissionMapper.findByCondition(userName,identity,realName);
        // 获取分页信息
        PageInfo<UserPermissionInfo> pageInfo = new PageInfo<>(UserPermissionInfoList);
        // 封装分页信息返回给前端
        ResPage<List<UserPermissionInfo>> resPage = new ResPage<>();
        resPage.setPage(page);  // 当前页
        resPage.setSize(pageSize);  // 页大小
        resPage.setTotalPage(pageInfo.getPages());  // 总页数
        resPage.setTotal(pageInfo.getTotal());      // 总条数
        resPage.setData(UserPermissionInfoList);

        // 返回数据
        return resPage;
    }

    @Override
    public void update(UserPermissionInfo userPermissionInfo) {
        userPermissionMapper.edit(userPermissionInfo);
    }

    @Override
    public boolean deleteByIds(String[] ids) {
        return userPermissionMapper.deleteByIds(ids) == ids.length;
    }
}
