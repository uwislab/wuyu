package com.fiveup.core.fuScale.develop_09.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fiveup.core.fuScale.develop_09.common.R;
import com.fiveup.core.fuScale.develop_09.entity.Fu_score;
import com.fiveup.core.fuScale.develop_09.service.FindService;
import com.fiveup.core.management.common.CommonResponse;
import com.github.pagehelper.PageHelper;
import jnr.ffi.annotations.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.util.RecordFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/Find")
public class FindController {
    @Autowired(required = true)
    @Lazy
    private FindService findService;

    /**
     * 考核查询
     * @return
     */
    @GetMapping("/page")
    public R<Page<Fu_score>> page (int page, int pageSize,String title,int id,String type, Integer upid){
        //构造分页构造器
        Page<Fu_score> pageInfo = new Page<>(page, pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Fu_score> queryWrapper = new LambdaQueryWrapper<>();
        PageHelper.clearPage();
        //添加过滤条件(id或者title)
        queryWrapper.eq(Fu_score::getUpid, upid);
        if(id != 0){
            queryWrapper.eq(Fu_score::getId, id);
        }
        if (title != null) {
            queryWrapper.like(Fu_score::getTitle, title);
        }
        if (type != null) {
            queryWrapper.eq(Fu_score::getType, type);
        }

        //添加排序条件
        queryWrapper.orderByDesc(Fu_score::getId);
        //执行查询
        Page<Fu_score> result = findService.page(pageInfo, queryWrapper);
        return R.success(result);
    }


    /**
     * 删除考核
     * @param id
     * @return
     */
    @PostMapping("/delete")
    public R<String> delete(int id) {

        System.out.println(id);
        findService.removeById(id);

        return R.success("删除成功！");
    }


    @GetMapping("/look")
    public R<Fu_score> look(int id,int upid) {
        return R.success(findService.getOne(new LambdaQueryWrapper<Fu_score>().eq(Fu_score::getId, id).eq(Fu_score::getUpid, upid)));

    }

    /**
     * 修改考核
     * @param fu_score
     * @return
     */
    @PostMapping("/change")
    public R<String> change(@RequestBody Fu_score fu_score) {
        UpdateWrapper<Fu_score> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", fu_score.getId()).eq("upid", fu_score.getUpid());
        updateWrapper.set("title", fu_score.getTitle());
        updateWrapper.set("type", fu_score.getType());
        updateWrapper.set("starttime", fu_score.getStarttime());
        updateWrapper.set("finishtime", fu_score.getFinishtime());
        updateWrapper.set("zhibiao", fu_score.getZhibiao());
        updateWrapper.set("score", fu_score.getScore());
        updateWrapper.set("beizhu", fu_score.getBeizhu());

        findService.update(updateWrapper);
        return R.success("修改成功！");
    }

    /**
     * 添加考核
     * @param fu_score
     * @return
     */
    @PostMapping("/add")
    public R<String> add(@RequestBody Fu_score fu_score) {
        findService.save(fu_score);
        return R.success("添加成功！");
    }

//    @GetMapping("/getExam")
//    public CommonResponse getScaleExam(){
//
//        return CommonResponse.ok();
//    }
}
