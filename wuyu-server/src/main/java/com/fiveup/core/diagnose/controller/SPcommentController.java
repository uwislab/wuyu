package com.fiveup.core.diagnose.controller;


import com.fiveup.core.common.result.Result;
import com.fiveup.core.diagnose.bean.student_Comment;
import com.fiveup.core.diagnose.service.SPcommentService;
import com.sun.net.httpserver.HttpContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/diagnose")
public class SPcommentController {

    @Autowired
    SPcommentService sPcommentService;
    @GetMapping("/SPcomment")
    @ResponseBody
    public Result doCommnet(@RequestParam("id") Long id, @RequestParam("comment") String comment){
        sPcommentService.doComment(id,comment);
        return Result.ok();
    }
    @GetMapping("/getcomment")
    @ResponseBody
    public Result getComment(@RequestParam("id") Long id){
        student_Comment sc = sPcommentService.getComment(id);
        if(sc!=null){
            return Result.ok(sc);
        }
        return Result.fail();
    }
}
