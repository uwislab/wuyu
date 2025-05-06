package com.fiveup.core.webUser.controller;

import com.fiveup.core.management.model.School;
import com.fiveup.core.webUser.service.WebUserService;
import com.fiveup.core.webUser.entity.webUser;
import com.fiveup.core.webUser.entity.webUserList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/webUser")
public class WebUserController {
    @Autowired
    private WebUserService webUserService;

    // 单个用户增加
    @PostMapping("/add")
    public boolean addWebUser(@RequestBody webUser user) {
        return webUserService.addWebUser(user);
    }

    // 单个用户删除
    @DeleteMapping("/delete/{id}")
    public boolean deleteWebUserById(@PathVariable int id) {
        return webUserService.deleteWebUserById(id);
    }

    // 批量删除
    @DeleteMapping("/deleteBatch")
    public boolean deleteWebUsersByIds(@RequestBody List<Integer> ids) {
        return webUserService.deleteWebUsersByIds(ids);
    }

    // 查询单个用户
    @GetMapping("/get/{id}")
    public webUser getWebUserById(@PathVariable int id) {
        return webUserService.getWebUserById(id);
    }

    // 分页查询
    @GetMapping("/list")
    public webUserList getWebUsersByPage(
            @RequestParam int curPage,
            @RequestParam int pageSize,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String realName,
            @RequestParam(required = false) Integer identity,
            @RequestParam(required = false) Integer gender
    ) {
        return webUserService.getWebUsersByPage(curPage, pageSize, username, realName, identity, gender);
    }

    // 返回所有用户
    @GetMapping("/all")
    public List<webUser> getAllWebUsers() { return webUserService.getAllWebUsers(); }

    // 修改
    @PutMapping("/update")
    public boolean updateWebUser(@RequestBody webUser user) {
        return webUserService.updateWebUser(user);
    }

    @GetMapping("/schools")
    public List<School> getAllSchools() {
        return webUserService.getAllSchools();
    }
}
