package com.fiveup.core.collection.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fiveup.core.collection.model.ColStudent;
import com.fiveup.core.collection.service.ColStudentService;
import com.fiveup.core.common.api.CommonPage;
import com.fiveup.core.common.api.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Copyright (C), 2015-2021, XXX有限公司
 * @ClassName: ColStudentController
 * @Author: zhouz
 * @E-mail: 邮箱
 * @Date: 2021/4/20 19:09
 * @Version: V1.0
 * @Description: 学生信息控制类
 */
@RestController
@RequestMapping("/api/studentinfo")
public class ColStudentController {

    @Autowired
    private ColStudentService colStudentService;

    //获得全部学生数据
    @RequestMapping(value = "/listAll",method = RequestMethod.GET)
    @ResponseBody
    public List<ColStudent> getList(){
        return colStudentService.list();
    }

    //获得学生数据
    @RequestMapping(value = "/getstudent/{id}",method = RequestMethod.GET)
    public ColStudent getItem(@PathVariable("id") Long id) {
        return colStudentService.getStudent(id);
    }

    //添加学生数据
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public ColStudent add(ColStudent colStudent){
        int count = colStudentService.addStudent(colStudent);
        if (count ==1){
            return colStudent;
        }else {
            return null;
        }
    }

    //删除学生数据
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)

    public ColStudent delete(@PathVariable("id") String id){
        System.out.println(id);
        long l = Long.parseLong(id);
        boolean count = colStudentService.deleteStudent(l);
        if(count) {
            return null;
        }else {
            return null;
        }
    }

    //批量删除学生
    @RequestMapping(value = "/delete/batch",method = RequestMethod.POST)
    @ResponseBody
    public ColStudent deleteClass(@RequestParam("ids") List<Long> ids){
        boolean count = colStudentService.deleteStudent(ids);
        if (count) {
            return null;
        } else {
            return null;
        }
    }

    //更新学生数据
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public ColStudent update(ColStudent colStudent){
        System.out.println("============================"+colStudent.toString());
        int count = colStudentService.updateStudent(colStudent);
        if (count == 1) {
            return colStudent;
        } else {
            return null;
        }
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<ColStudent>> getList(@RequestParam(value = "keyword", required = false) String keyword,
                                                        @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                        @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        Page<ColStudent> studentList = colStudentService.listStudent(keyword, pageNum, pageSize);
        System.out.println("++++++++++++++++++++++"+CommonPage.restPage(studentList));
        return CommonResult.success(CommonPage.restPage(studentList));
    }
}
