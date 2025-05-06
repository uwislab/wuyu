package com.fiveup.core.cultivation.controller;

import cn.hutool.aop.interceptor.SpringCglibInterceptor;
import com.fiveup.core.common.util.ExcelExportUtil;
import com.fiveup.core.cultivation.entity.*;
import com.fiveup.core.cultivation.entity.vo.Pager;
import com.fiveup.core.cultivation.enums.GoalStates;
import com.fiveup.core.cultivation.service.*;
import com.fiveup.core.cultivation.service.impl.UploadExcelServiceImpl;
import com.fiveup.core.cultivation.untils.Constants;
import com.fiveup.core.management.common.CommonResponse;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.util.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.time.Duration;
import java.util.*;

/**
 * @author Harvi
 */
@RestController
@RequestMapping("/api/goal")
public class GoalController {
    private final GoalService goalService;
    private final BasicTeacherService basicTeacherService;
    private final GuidanceService guidanceService;
    private final GoalAspectService goalAspectService;
    private final AspectActivityService aspectActivityService;

    @Autowired
    public GoalController(GoalService goalService,
                          BasicTeacherService basicTeacherService,
                          GuidanceService guidanceService,
                          GoalAspectService goalAspectService,
                          AspectActivityService aspectActivityService) {
        this.goalService = goalService;
        this.basicTeacherService = basicTeacherService;
        this.guidanceService = guidanceService;
        this.goalAspectService = goalAspectService;
        this.aspectActivityService = aspectActivityService;
    }

    //新增评价量表
    @RequestMapping("/insert")
    public CommonResponse insert(@RequestBody Goal goal) {
        Date date = new Date();
//        System.out.println("getId:"+goal.getId());
        if (goal.getId() == null) {
            goal.setCreateDate(date);
        }
        goal.setModifyDate(date);

        //ctvgoal的gid是ctv_guidance的id
        Boolean flag = goalService.saveOrUpdate(goal);

        Guidance guidance = guidanceService.getById(goal.getGid());
        if (Objects.equals(guidance.getGid(), "")) {
            guidance.setGid(goal.getId().toString() + Constants.SLASH);
        } else {
            guidance.setGid(guidance.getGid() + goal.getId().toString() + Constants.SLASH);
        }
        guidanceService.updateById(guidance);
        return CommonResponse.ok();
    }

    @RequestMapping("/info")
    public CommonResponse<Goal> info(@RequestBody Goal goal) {
        goal = goalService.getById(goal.getId());
        setEditorNames(goal);
        return CommonResponse.ok(goal);
    }
    @RequestMapping("/list")
    public CommonResponse<List<Goal>> list() {
        List<Goal> l = goalService.getList(null);
        System.out.println("list:  "+l.size());
        l.forEach(i->setEditorNames(i));
        return CommonResponse.ok(l);
    }

    @GetMapping("/getIndicater")
    public CommonResponse<Indicator> getIndicator(Integer id) {
        System.out.println("insert");
        Indicator indicator = goalService.getIndicator(id);
        System.out.println("--------"+indicator.toString());
        return CommonResponse.ok(indicator);
    }

    @RequestMapping("/updateIndicater")
    public CommonResponse<Void> updateIndicator(@RequestBody Indicator indicator) {
        goalService.updateIndicator(indicator);
        return CommonResponse.ok();
    }



    //显示为脑图
    @GetMapping("/details/{id}/{default}")
    public CommonResponse<Map<String, Object>> detail(@PathVariable(value="id") Integer id,@PathVariable(value="default") Boolean tags) {
        Goal goal = goalService.getById(id);
        setEditorNames(goal);
        //通过gid查询ctv_goal_aspect
        GoalAspect aspect = new GoalAspect();
        //根据条件查询默认的量表
        if(tags){
            id=1;
        }
        aspect.setGid(id);
        //二级节点
        List<GoalAspect> aspects = goalAspectService.getListByGid(aspect);
        //获取子节点
        AspectActivity aspectActivity = new AspectActivity();
        aspectActivity.setAid(id);
        Map<String, Object> goalToAspects = new HashMap<>(2);
        List<AspectActivity> aspectActivityList = aspectActivityService.getListByAid(aspectActivity);
        //封装结果
        goalToAspects.put("goal",goal);
        goalToAspects.put("root",aspects);
        goalToAspects.put("child",aspectActivityList);

        return CommonResponse.ok(goalToAspects);
    }

    //状态改变，管理员审核功能完成
    @RequestMapping("/changeState")
    public CommonResponse<Void> changeState(@RequestBody Goal goal) {

        System.out.println(goal.getState());
        System.out.println(GoalStates.REVIEWING.getGoalState());

        if (goal.getState().equals(GoalStates.REVIEWING.getGoalState())) {
            GoalAspect aspect = new GoalAspect();
            aspect.setGid(goal.getId());
            List<GoalAspect> aspects = goalAspectService.getListByGid(aspect);

            if (aspects == null || aspects.size() == 0) {
                return CommonResponse.fail(1001,"无法提交审核：Goal 未添加 Aspect");
            }
            for (GoalAspect a: aspects) {
                AspectActivity activity = new AspectActivity();
                activity.setAid(a.getId());
                List<AspectActivity> activities = aspectActivityService.getListByAid(activity);

//                if (activities.size() == 0) {
//                    return RespFactory.fail("无法提交审核：" + a.getType() + "未添加 Activity");
//                }

                double weightSum = 0;
                for (AspectActivity aa: activities) {
                    weightSum += aa.getWeight();
                }
//                if (weightSum + 0.01 < 1) {
//                    return RespFactory.fail("无法提交审核：" + a.getType() + "的 Activity 权重和不为 1");
//                }
            }
        }
        goalService.updateById(goal);
        return CommonResponse.ok();
    }

    //根据id删除评价量表
    @DeleteMapping("delete/{id}")
    public CommonResponse<Void> delete(@PathVariable Integer id) {
        Guidance guidance = guidanceService.getById(goalService.getById(id).getGid());
        guidance.setGid(guidance.getGid().replace(id + Constants.SLASH, ""));
        guidanceService.updateById(guidance);
        goalService.removeById(id);
        goalAspectService.deleteByGid(id);
        aspectActivityService.deleteByAid(id);
        return CommonResponse.ok();
    }

    //根据条件查询评价量表信息
    @RequestMapping("/paging")
    public CommonResponse paging(@RequestBody Goal goal) {
        Pager pager = new Pager(goal);
        Date endDate = new Date();
        List<Goal> list;
        PageHelper.startPage(pager.getNo(), pager.getSize(), "launch_date desc");
        list = goalService.getList(goal);
        //处理逾期的表
        for(Goal item : list){
            //判断是否在6年之内,逾期则更新记录
            if(Duration.between(item.getLaunchDate().toInstant(),endDate.toInstant()).getSeconds()>(6 * 365 * 24 * 60 * 60)){
                item.setState(9);
                goalService.updateById(item);
            }
        }
        PageInfo<Goal> pageInfo = new PageInfo<>(list);
        for (Goal g : pageInfo.getList()) {
            setEditorNames(g);
        }
        return CommonResponse.ok(pageInfo);
    }

    //解析excel方法
    @RequestMapping(value="uploadExcel")
    public CommonResponse uploadExcel(@RequestParam("file") MultipartFile file){
        uploadExcelService uploadExcelService= new UploadExcelServiceImpl();
        List<ExcelExportUtil> list = uploadExcelService.uploadExcel(file);
        return CommonResponse.ok(list);
    }

    //下载模板方法
    @GetMapping(value="/downlaodTemplateWord")
    public void downloadTemplateWord(HttpServletResponse response, HttpServletRequest request){
        String fileName = "模板量表.xlsx";
        ClassPathResource resource = new ClassPathResource("templates/" + fileName);
        InputStream inputStream = null;
        try {
            inputStream = resource.getInputStream();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            IOUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally {
            try {
                assert inputStream != null;
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setEditorNames(Goal goal) {
        if (goal != null) {
            Map<Integer, String> map = new HashMap<>(100);
            List<BasicTeacher> teachers = basicTeacherService.getList();
            for (BasicTeacher teacher: teachers) {
                map.put(teacher.getId(), teacher.getTeacherName());
            }
            goal.setEditorNames(mapForNames(goal.getEditorTids(), map));
        }
    }

    private String mapForNames(String tids, Map<Integer, String> map) {
        StringBuilder names = new StringBuilder();
        for (String tid: tids.replace(Constants.RIGHT_BRACKET + Constants.LEFT_BRACKET, Constants.AT)
                .replace(Constants.LEFT_BRACKET, "").replace(Constants.RIGHT_BRACKET, "").split(Constants.AT)) {
            names.append(map.get(Integer.parseInt(tid))).append(Constants.COMMA);
        }
        return names.toString();
    }
}
