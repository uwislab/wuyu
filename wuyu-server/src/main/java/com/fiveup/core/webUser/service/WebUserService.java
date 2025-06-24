package com.fiveup.core.webUser.service;

import com.fiveup.core.management.model.School;
import com.fiveup.core.webUser.entity.webUser;
import com.fiveup.core.webUser.entity.webUserList;

import java.util.List;

public interface WebUserService {
    // 单个增加
    boolean addWebUser(webUser user);

    // 单个删除
    boolean deleteWebUserById(int id);

    // 批量删除
    boolean deleteWebUsersByIds(List<Integer> ids);

    // 查询单个
    webUser getWebUserById(int id);

    // 查询所有用户
    List<webUser> getAllWebUsers();

    // 分页查询
    webUserList getWebUsersByPage(int curPage, int pageSize, String username, String realName, Integer identity, Integer gender);

    // 修改
    boolean updateWebUser(webUser user);

    List<School> getAllSchools();
}
