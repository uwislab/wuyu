package com.fiveup.core.cultivation.controller;

import com.fiveup.core.cultivation.entity.AspectActivity;
import com.fiveup.core.cultivation.entity.vo.Pager;
import com.fiveup.core.cultivation.service.AspectActivityService;
import com.fiveup.core.management.common.CommonResponse;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @author Harvi
 */
@RestController
@RequestMapping("/api/aspectActivity")
public class AspectActivityController {

    private final AspectActivityService aspectActivityService;

    @Autowired
    public AspectActivityController(AspectActivityService aspectActivityService) {
        this.aspectActivityService = aspectActivityService;
    }

    //插入评价内容
    @RequestMapping("/insert")
    public CommonResponse insert(@RequestBody AspectActivity activity) {
        // 权重和
        double weightSum = 0;
        // 获取跟当前 Activity 所关联的同一个 Aspect 的所有 Activity
        List<AspectActivity> activities = aspectActivityService.getListByAid(activity);
        if (activity.getId() == null) {
            activity.setCreateDate(new Date());
            for (AspectActivity a: activities){
                weightSum += a.getWeight();
            }
            if (weightSum + activity.getWeight() > 100.0) {
                return CommonResponse.fail(1001,"添加失败，请检查输入的分数是否大于100！");
            }
        } else {
            for (AspectActivity a: activities) {
                // 跳过正在被编辑的 Activity 的权重
                if (a.getId().equals(activity.getId())) {
                    continue;
                }
                weightSum += a.getWeight();
            }
            if (weightSum + activity.getWeight() > 100.0) {
                return CommonResponse.fail(1002,"添加失败，请检查输入的分数是否大于100！");
            }
        }
        activity.setModifyDate(new Date());
        boolean flag=aspectActivityService.saveOrUpdate(activity);
        if (flag) {
            return CommonResponse.ok();
        }else{
            return CommonResponse.fail(1003,"添加失败，请联系管理员！");
        }
    }
    //删除评价内容
    @RequestMapping("/delete/{id}")
    public CommonResponse delete(@PathVariable Integer id) {
        boolean flag=aspectActivityService.removeById(id);
        if(flag) {
            return CommonResponse.ok();
        }else {
            return CommonResponse.fail(1001,"删除失败，请联系管理员！");
        }
    }

    @RequestMapping("/paging")
    public Object paging(@RequestBody AspectActivity activity) {
        Pager pager = new Pager(activity);
        PageHelper.startPage(pager.getNo(), pager.getSize(), "modify_date desc");
        List<AspectActivity> activities = aspectActivityService.getListByAid(activity);
        return new PageInfo<>(activities, pager.getSize());
    }
}
