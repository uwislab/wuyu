package com.fiveup.core.webUser.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fiveup.core.management.mapper.SchoolMapper;
import com.fiveup.core.management.model.School;
import com.fiveup.core.webUser.service.WebUserService;
import com.fiveup.core.webUser.entity.webUser;
import com.fiveup.core.webUser.entity.webUserList;
import com.fiveup.core.webUser.mapper.WebUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebUserServiceImpl implements WebUserService {
    @Autowired
    private WebUserMapper webUserMapper;

    @Autowired
    private SchoolMapper schoolMapper;
    // 单个增加

    @Override
    public boolean addWebUser(webUser user) {
        return webUserMapper.insert(user) > 0;
    }

    // 单个删除
    @Override
    public boolean deleteWebUserById(int id) {
        return webUserMapper.deleteById(id) > 0;
    }

    // 批量删除
    @Override
    public boolean deleteWebUsersByIds(List<Integer> ids) {
        return webUserMapper.deleteBatchIds(ids) > 0;
    }

    // 查询单个
    @Override
    public webUser getWebUserById(int id) {
        return webUserMapper.selectById(id);
    }

    // 查询所有用户
    @Override
    public List<webUser> getAllWebUsers() { return webUserMapper.selectList(null); }

    // 分页查询
    @Override
    public webUserList getWebUsersByPage(int curPage, int pageSize, String username, String realName, Integer identity, Integer gender) {
        QueryWrapper<webUser> queryWrapper = new QueryWrapper<>();
        if (username != null && !username.isEmpty()) {
            queryWrapper.like("username", username);
        }
        if (realName != null && !realName.isEmpty()) {
            queryWrapper.like("real_name", realName);
        }
        if (identity != null) {
            queryWrapper.eq("identity", identity);
        }
        if (gender != null) {
            queryWrapper.eq("gender", gender);
        }

        Page<webUser> page = new Page<>(curPage, pageSize);
        Page<webUser> resultPage = webUserMapper.selectPage(page, queryWrapper);
        return new webUserList(
                resultPage.getRecords(),
                (int) resultPage.getCurrent(),
                (int) resultPage.getSize(),
                (int) resultPage.getPages(),
                (int) resultPage.getTotal(),
                resultPage.hasNext()
        );
    }

    // 修改
    @Override
    public boolean updateWebUser(webUser user) {
        return webUserMapper.updateById(user) > 0;
    }

    @Override
    public List<School> getAllSchools() {
        return schoolMapper.getAllSchools();
    }
}
