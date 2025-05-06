package com.fiveup.core.cultivation.controller;

import com.fiveup.core.cultivation.entity.Minutes;
import com.fiveup.core.cultivation.service.MinutesService;
import com.fiveup.core.cultivation.entity.vo.Result;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author Harvi
 */
@RestController
@RequestMapping("/api/minutes")
public class MinutesController {
    private final MinutesService minutesService;

    @Autowired
    public MinutesController(MinutesService minutesService) {
        this.minutesService = minutesService;
    }

    @RequestMapping("/paging")
    public Object paging(@RequestBody Minutes o){
        int pageNo = o.getPageNo() == null ? 1 : o.getPageNo();
        int pageSize = o.getPageSize() == null ? 10 : o.getPageSize();
        PageHelper.startPage(pageNo,pageSize,"create_date desc");
        return new PageInfo<>(minutesService.getAll(o), pageSize);
    }

    @RequestMapping("/insert")
    public Result<Void> insert(@RequestBody Minutes o)   {
        Result<Void> result = new Result<>();
        Minutes minutes = minutesService.getOne(o.getId());

        if (minutes == null || minutes.getIsDeleted()) {
            minutes = new Minutes();
            minutes.setTid(o.getTid());
            minutes.setMid(o.getMid());
            minutes.setCreateDate(new Date());
        }
        minutes.setContent(o.getContent());
        minutes.setModifyDate(new Date());
        minutesService.saveOrUpdate(minutes);
        result.setCode(200);
        result.setMsg("Success");
        return result;

    }

    @RequestMapping("/details")
    public Result<Minutes> details(@RequestBody Minutes o)   {
        Result<Minutes> result = new Result<>();
        Minutes minutes = minutesService.getOne(o.getId());
        result.setCode(200);
        result.setMsg("Success");
        result.setData(minutes);
        return result;
    }

    @RequestMapping("/delete")
    public Result<Void> delete(@RequestBody Minutes o)   {
        Result<Void> result = new Result<>();
        o.setIsDeleted(true);
        minutesService.updateById(o);
        result.setCode(200);
        result.setMsg("Success");
        return result;
    }
}
