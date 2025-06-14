package com.fiveup.core.permission.controller;

import com.fiveup.core.commentgeneration.utils.Result;
import com.fiveup.core.courseScore.entity.ResPage;
import com.fiveup.core.permission.entity.UserPermissionInfo;
import com.fiveup.core.permission.service.UserPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/UserPermission")
public class UserPermissionController {
    @Autowired
    private UserPermissionService userPermissionService;

    /**
     * 条件分页查询
     * @param
     */
    @PostMapping("/get")
    public Result search(@RequestParam String userName, @RequestParam(required = false) Integer identity, @RequestParam String realName, @RequestParam Integer page, @RequestParam Integer pageSize){

        ResPage<List<UserPermissionInfo>> resPage = userPermissionService.search(userName,identity,realName,page,pageSize);
        Result result = new Result(200,"查询成功", Math.toIntExact(resPage.getTotal()),resPage);
        return result;
    }

    //查询所有用户
    @PostMapping("/GetAll")
    public List<UserPermissionInfo> QuerryAll(){
        List<UserPermissionInfo> list = userPermissionService.getAll();
        return list;
    }

    @PostMapping("/deleteByIds")
    public Result deleteByIds(@RequestBody String[] ids){
        if(ids.length==0)
            return new Result(233,"选择数据不能为空!");
        boolean deleteSuccess = userPermissionService.deleteByIds(ids);
        Result result = new Result();
        if(deleteSuccess) {
            result.setCode(200);
            result.setMsg("删除成功！");
        }else{
            result.setCode(233);
            result.setMsg("删除失败!");
        }
        return result;
    }

    @PostMapping("/edit")
    public Result edit(@RequestBody UserPermissionInfo userPermissionInfo){
        userPermissionService.update(userPermissionInfo);
        Result result = new Result();
        result.setCode(600);
        result.setMsg("修改成功！");
        return result;
    }

    @GetMapping("/ok")
    public String OK(){
        return "OK";
    }
}
