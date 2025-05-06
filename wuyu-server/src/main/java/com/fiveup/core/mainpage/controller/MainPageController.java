package com.fiveup.core.mainpage.controller;


import com.fiveup.core.mainpage.domain.po.WebUser;
import com.fiveup.core.mainpage.domain.vo.WebUserVo;
import com.fiveup.core.mainpage.mapper.MainPageMapper;
import com.fiveup.core.mainpage.service.MainPageService;
import com.fiveup.core.performanceevaluation.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/mainPage")
public class MainPageController {


    @Autowired
    private MainPageService mainPageService;
    @Autowired
    private MainPageMapper mainPageMapper;

    @GetMapping("{id}")
    public Result getWebUserById(@PathVariable("id") String id) {
        WebUserVo user = mainPageService.getWebUserById(id);
        if (user != null) {
            return new Result(200, "获取个人信息成功", 1, user);
        }
        return new Result(500, "获取个人信息失败");
    }

    @PutMapping("{id}")
    public Result updateWebUserById(@PathVariable("id") int id, @RequestBody WebUser webUser) {
        if (mainPageService.updateWebUserById(id, webUser) == 1) {
            return new Result(200, "更新成功", mainPageService.updateWebUserById(id, webUser));
        }
        return new Result(500, "更新失败", 0);
    }

    @PostMapping("{id}")
    public Result getPasswordById(@PathVariable("id") int id, @RequestBody WebUser webUser) {
        WebUser user = mainPageMapper.selectById(id);
        if (webUser.getPassword().equals(user.getPassword())) {
            return new Result(200, "原密码正确", true);
        }
        return new Result(500, "原密码错误", false);
    }
}