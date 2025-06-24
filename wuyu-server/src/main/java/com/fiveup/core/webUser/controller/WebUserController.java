package com.fiveup.core.webUser.controller;

import com.fiveup.core.common.controller.BaseController;
import com.fiveup.core.management.model.School;
import com.fiveup.core.webUser.service.WebUserService;
import com.fiveup.core.webUser.entity.webUser;
import com.fiveup.core.webUser.entity.webUserList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/webUser")
@Slf4j
public class WebUserController extends BaseController {
    @Autowired
    private WebUserService webUserService;

    // 单个用户增加
    @PostMapping("/add")
    public boolean addWebUser(@RequestBody webUser user, HttpServletRequest request) {
        // 记录操作用户
        String operatorName = getCurrentUsername(request);
        log.info("用户[{}]添加新用户: {}", operatorName, user.getUsername());
        
        // 默认设置为正常状态
        if (user.getStatus() == null) {
            user.setStatus(0);
        }
        return webUserService.addWebUser(user);
    }

    // 单个用户删除
    @DeleteMapping("/delete/{id}")
    public boolean deleteWebUserById(@PathVariable int id, HttpServletRequest request) {
        // 记录操作用户
        String operatorName = getCurrentUsername(request);
        log.info("用户[{}]删除用户ID: {}", operatorName, id);
        
        return webUserService.deleteWebUserById(id);
    }

    // 批量删除
    @DeleteMapping("/deleteBatch")
    public boolean deleteWebUsersByIds(@RequestBody List<Integer> ids, HttpServletRequest request) {
        // 记录操作用户
        String operatorName = getCurrentUsername(request);
        log.info("用户[{}]批量删除用户: {}", operatorName, ids);
        
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
    public boolean updateWebUser(@RequestBody webUser user, HttpServletRequest request) {
        // 记录操作用户
        String operatorName = getCurrentUsername(request);
        log.info("用户[{}]更新用户信息: {}", operatorName, user.getUsername());
        
        return webUserService.updateWebUser(user);
    }
    
    /**
     * 切换用户状态
     * @param id 用户ID
     * @param status 目标状态：0正常，1禁用
     * @return 操作结果
     */
    @PutMapping("/status/{id}")
    public Map<String, Object> updateUserStatus(
            @PathVariable int id,
            @RequestParam int status,
            HttpServletRequest request
    ) {
        // 记录操作用户
        String operatorName = getCurrentUsername(request);
        log.info("用户[{}]切换用户ID[{}]状态为: {}", operatorName, id, status == 0 ? "正常" : "禁用");
        
        boolean success = webUserService.updateUserStatus(id, status);
        
        Map<String, Object> result = new HashMap<>();
        result.put("success", success);
        result.put("message", success ? "状态更新成功" : "状态更新失败");
        return result;
    }
    
    /**
     * 批量切换用户状态
     * @param ids 用户ID列表
     * @param status 目标状态：0正常，1禁用
     * @return 操作结果
     */
    @PutMapping("/status/batch")
    public Map<String, Object> batchUpdateUserStatus(
            @RequestBody List<Integer> ids,
            @RequestParam int status,
            HttpServletRequest request
    ) {
        // 记录操作用户
        String operatorName = getCurrentUsername(request);
        log.info("用户[{}]批量切换用户状态为: {}, 用户IDs: {}", operatorName, status == 0 ? "正常" : "禁用", ids);
        
        boolean success = webUserService.batchUpdateUserStatus(ids, status);
        
        Map<String, Object> result = new HashMap<>();
        result.put("success", success);
        result.put("message", success ? "状态批量更新成功" : "状态批量更新失败");
        return result;
    }

    @GetMapping("/schools")
    public List<School> getAllSchools() {
        return webUserService.getAllSchools();
    }
}
