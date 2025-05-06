package com.fiveup.core.collection.controller;

import com.fiveup.core.collection.model.ColTeacher;
import com.fiveup.core.collection.service.ColTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Copyright (C), 2015-2021, XXX有限公司
 * @ClassName: ColTeacherController
 * @Author: 作者姓名(一般是写姓名的拼音)
 * @E-mail: 邮箱
 * @Date: 2021/4/21 10:53
 * @Version: V1.0
 * @Description: 对该类的描述
 */
@RestController
@RequestMapping("/teacherinfo")
public class ColTeacherController {

    @Autowired
    private ColTeacherService colTeacherService;

    //获得全部教师信息
    @RequestMapping(value = "/getteacherlist",method = RequestMethod.GET)
    @ResponseBody
    public List<ColTeacher> getList(){
        return colTeacherService.list();
    }

    //获得教师信息
    @RequestMapping(value = "/getteacher",method = RequestMethod.GET)
    @ResponseBody
    public ColTeacher getItem(@RequestParam("id")Long id){
        return colTeacherService.getTeacher(id);
    }
}
