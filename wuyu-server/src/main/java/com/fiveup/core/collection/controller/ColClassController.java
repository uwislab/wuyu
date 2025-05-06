package com.fiveup.core.collection.controller;

import com.fiveup.core.collection.model.ColClass;
import com.fiveup.core.collection.service.ColClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/api/classinfo")
public class ColClassController {

    @Autowired
    private ColClassService colClassService;

    @RequestMapping(value = "/getclasslist",method = RequestMethod.GET)
    @ResponseBody
    public List<ColClass> getList(){
        return colClassService.list();
    }

    @RequestMapping(value = "/getclass",method = RequestMethod.GET)
    @ResponseBody
    public ColClass getItem(@RequestParam("id") Long id){
        return  colClassService.getById(id);
    }
}
