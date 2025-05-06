package com.fiveup.core.questionnaire.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fiveup.core.common.result.Result;
import com.fiveup.core.questionnaire.entity.TeachingProgram;
import com.fiveup.core.questionnaire.service.TeachingProgramService;
import com.fiveup.core.questionnaire.vo.TeachingProgramVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
教学大纲控制层
 */
@RestController
@RequestMapping("/api/teaching_program")
public class TeachingProgramController {

    @Autowired
    TeachingProgramService teachingProgramService;

    @ApiOperation(value = "获取所有医院设置")
    @GetMapping("findAll")
    public Result findAll(){
        List<TeachingProgram> list = teachingProgramService.list();
        return  Result.ok(list);
    }

    @ApiOperation(value = "逻辑删除医院信息")
    @DeleteMapping("{id}")
    public Result remove(@PathVariable Long id){
        boolean flag = teachingProgramService.removeById(id);
        if(flag){
            return Result.ok();
        }else{
            return Result.fail();
        }
    }

    @ApiOperation(value = "条件查询带分页")
    @PostMapping("findPage/{current}/{limit}")
    public Result findPage(@PathVariable long current,
                           @PathVariable long limit,
                           @RequestBody(required = false) TeachingProgramVo teachingProgramVo){
//        创建 Page对象，传递当前页，每页记录数
        System.out.println(limit);
        System.out.println(current);
        Page<TeachingProgram> page = new Page<>(current, limit);
//        构建条件
        QueryWrapper<TeachingProgram> wrapper=new QueryWrapper<>();

        String title = teachingProgramVo.getTitle();//教案的标题
        String code = teachingProgramVo.getCode();//教案的编号
        if(!StringUtils.isEmpty(title)){
            wrapper.like("title", teachingProgramVo.getTitle());
        }
        if (!StringUtils.isEmpty(code)){
            wrapper.eq("code", teachingProgramVo.getCode());
        }
//       调用方法实现分页查询
        Page<TeachingProgram> pageTeachingProgram = teachingProgramService.page(page, wrapper);
//        返回结果
        return Result.ok(pageTeachingProgram);
    }

    @ApiOperation(value = "添加教案")
    @PostMapping("save")
    public Result save(@RequestBody TeachingProgram hospitalSet){
        boolean save = teachingProgramService.save(hospitalSet);
        if (save){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }

    @ApiOperation(value = "根据id获取教案")
    @GetMapping("getById/{id}")
    public Result getById(@PathVariable Long id){
        TeachingProgram teachingProgram = teachingProgramService.getById(id);
        return Result.ok(teachingProgram);
    }

    @ApiOperation(value = "修改教案")
    @PostMapping("update")
    public Result update(@RequestBody TeachingProgram teachingProgram){
        boolean flag = teachingProgramService.updateById(teachingProgram);
        if (flag){
            return Result.ok();
        }else{
            return Result.fail();
        }
    }

    @ApiOperation(value = "批量删除教案设置")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList){

        teachingProgramService.removeByIds(idList);
        return Result.ok();
    }

}
