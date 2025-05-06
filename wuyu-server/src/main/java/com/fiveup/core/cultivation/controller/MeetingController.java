package com.fiveup.core.cultivation.controller;

import com.fiveup.core.cultivation.entity.Meeting;
import com.fiveup.core.cultivation.entity.vo.Result;
import com.fiveup.core.cultivation.service.MeetingService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @author Harvi
 */
@RestController
@RequestMapping("/api/meeting")
public class MeetingController {
    private final MeetingService meetingService;

    @Autowired
    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @RequestMapping("/paging")
    public Object paging(@RequestBody Meeting o) {
        int pageNo = o.getPageNo() == null ? 1 : o.getPageNo();
        int pageSize = o.getPageSize() == null ? 10 : o.getPageSize();
        PageHelper.startPage(pageNo, pageSize, " launch_date desc ");
        return new PageInfo<>(meetingService.getAll(o), pageSize);
    }

    @RequestMapping("/insert")
    public Result<Void> insert(@RequestBody Meeting o)   {
        Result<Void> result = new Result<>();
        Meeting meeting = meetingService.getOne(o.getId());
        if (meeting == null || meeting.getIsDeleted()) {
            meeting = new Meeting();
            meeting.setTid(o.getTid());
            meeting.setCreateDate(new Date());
        }
        meeting.setTitle(o.getTitle());
        meeting.setTarget(o.getTarget());
        meeting.setSummary(o.getSummary());
        meeting.setLaunchDate(o.getLaunchDate());
        meeting.setModifyDate(new Date());
        meetingService.saveOrUpdate(meeting);

        result.setCode(200);
        result.setMsg("Success");
        return result;
    }

    @RequestMapping("/list")
    public Object list() {
        return meetingService.getAll(new Meeting());
    }

    @RequestMapping("/details")
    public Result<Meeting> details(@RequestBody Meeting o)   {
        Result<Meeting> result = new Result<>();
        Meeting meeting = meetingService.getOne(o.getId());
        result.setCode(200);
        result.setMsg("Success");
        result.setData(meeting);
        return result;
    }

    @RequestMapping("/delete")
    public Result<Void> delete(@RequestBody Meeting o)   {
        Result<Void> result = new Result<>();
        o = meetingService.getOne(o.getId());
        o.setIsDeleted(true);
        meetingService.updateById(o);
        result.setCode(200);
        result.setMsg("Success");
        return result;
    }
}