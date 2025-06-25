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

    /**
     * 切换用户状态
     * @param id 用户ID
     * @param status 目标状态：0正常，1禁用
     * @return 操作是否成功
     */
    boolean updateUserStatus(int id, int status);
    
    /**
     * 批量更新用户状态
     * @param ids 用户ID列表
     * @param status 目标状态：0正常，1禁用
     * @return 操作是否成功
     */
    boolean batchUpdateUserStatus(List<Integer> ids, int status);

    List<School> getAllSchools();
}
