package com.fiveup.core.cultivation.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fiveup.core.common.util.FreemarkerExportWordUtil;
import com.fiveup.core.cultivation.entity.AspectActivity;
import com.fiveup.core.cultivation.entity.WordTemplate;
import com.fiveup.core.cultivation.entity.vo.Result;
import com.fiveup.core.cultivation.entity.GoalAspect;
import com.fiveup.core.cultivation.entity.vo.Pager;
import com.fiveup.core.cultivation.service.AspectActivityService;
import com.fiveup.core.cultivation.service.GoalAspectService;
import com.fiveup.core.cultivation.untils.RespFactory;
import com.fiveup.core.management.common.CommonResponse;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.*;

/**
 * @author Harvi
 */
@RestController
@RequestMapping("/api/goalAspect")
public class GoalAspectController {
    private final GoalAspectService goalAspectService;
    private final AspectActivityService aspectActivityService;

    @Autowired
    public GoalAspectController(GoalAspectService goalAspectService, AspectActivityService aspectActivityService) {
        this.goalAspectService = goalAspectService;
        this.aspectActivityService = aspectActivityService;
    }

    @RequestMapping("/info")
    public Result<GoalAspect> info(@RequestBody GoalAspect aspect) {
        Result<GoalAspect> result = new Result<>();
        result.setCode(200);
        result.setMsg("Success");
        result.setData(goalAspectService.getById(aspect.getId()));
        return result;
    }

    //增加维度和内容
    @RequestMapping("/insert")
    public CommonResponse insert(@RequestBody GoalAspect aspect) {
        List<GoalAspect> aspects = goalAspectService.getListByGid(aspect);
        aspect.setCreateDate(new Date());
        aspect.setModifyDate(new Date());
        if(goalAspectService.save(aspect)){
            return CommonResponse.ok();
        }
        else{
            return CommonResponse.fail(-1,"插入失败");
        }
    }

    @RequestMapping("/delete/{child}")
    public Result<Void> delete(@RequestBody GoalAspect aspect, @PathVariable(value="child") Boolean child) {
        if(child){
            aspectActivityService.deleteById(aspect.getId());
        }else{
            goalAspectService.removeById(aspect.getId());
        }
        return RespFactory.success();
    }

    @RequestMapping("/paging")
    public Object page(@RequestBody GoalAspect aspect){
        Pager pager = new Pager(aspect);
        PageHelper.startPage(pager.getNo(), pager.getSize(), "modify_date desc");
        LambdaQueryWrapper<AspectActivity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(AspectActivity::getAid,aspect.getGid());
        //两次才会有结果
        List<AspectActivity> aspectActivities = aspectActivityService.list(lambdaQueryWrapper);
        aspectActivities=aspectActivityService.list(lambdaQueryWrapper);
        List<GoalAspect> aspects = goalAspectService.getListByGid(aspect);
        List<Object> list = new ArrayList<>();
        list.add(aspects);
        list.add(aspectActivities);
        return new PageInfo<>(list, pager.getSize());
    }

    /**
     * insertchild
     *
     * @param aspectActivity 插入子节点
     * @return {@link CommonResponse}
     */
    @RequestMapping(value = "/insertchild",method = RequestMethod.POST)
    public CommonResponse insertchild(@RequestBody AspectActivity aspectActivity){
        aspectActivity.setCreateDate(new Date());
        aspectActivity.setModifyDate(new Date());
        if(aspectActivityService.saveOrUpdate(aspectActivity)) {
            return CommonResponse.ok();
        }else{
            return CommonResponse.fail(-1,"操作失败");
        }
    }
    //更新三级节点后的维度
    @RequestMapping(value = "/updatechild",method = RequestMethod.POST)
    public CommonResponse updatechild(@RequestBody AspectActivity aspectActivity){
        aspectActivity.setModifyDate(new Date());
        //根据id更新记录
        if(aspectActivityService.updateById(aspectActivity)) {
            return CommonResponse.ok();
        }else{
            return CommonResponse.fail(-1,"操作失败");
        }
    }
    //更新维度
    @RequestMapping(value="/update")
    public CommonResponse update(@RequestBody GoalAspect goalAspect){
        goalAspect.setModifyDate(new Date());
        //根据id更新记录
        if(goalAspectService.updateById(goalAspect)){
            return CommonResponse.ok();
        }else{
            //失败返回
            return CommonResponse.fail(-1,"操作失败");
        }
    }
    @RequestMapping(value = "/exportword/{id}",method = RequestMethod.GET)
    public void exportword(HttpServletResponse response,@PathVariable(value="id")int id){
        GoalAspect aspect = new GoalAspect();
        aspect.setGid(id);
        //二级节点
        List<GoalAspect> aspects = goalAspectService.getListByGid(aspect);
        //获取子节点
        AspectActivity aspectActivity = new AspectActivity();
        aspectActivity.setAid(id);
        List<AspectActivity> aspectActivityList = aspectActivityService.getListByAid(aspectActivity);
        Map<String,Object> values = new HashMap<>();
        List<WordTemplate> lt = new ArrayList<>();
        //处理特殊情况，仅有一级节点时
        if(aspects.size()>0 && aspectActivityList.size()==0){
            for(GoalAspect item:aspects){
                WordTemplate wordTemplate = new WordTemplate();
                wordTemplate.setFirst(item.getType());
                wordTemplate.setTags(item.getType());
                lt.add(wordTemplate);
            }
        }
        //仅能生成三级深度的word，如果要更多深度，要重新设置template.ftl的结构
        for(AspectActivity item:aspectActivityList){
            WordTemplate wordTemplate = new WordTemplate();
            for(GoalAspect i:aspects){
                //分别对应word中的一级指标和二级指标
                if(item.getTitle().equals("二级评价指标")){
                    //二级指标（三级节点）的gid对应一级指标的id
                    if(item.getGid().equals(i.getId())){
                        wordTemplate.setFirst(i.getType());
                        wordTemplate.setSecond(item.getContent());
                    }
                }
            }
            //填充评分方式，因为tags这个字段只有后面节点才有
            if(item.getTags()!=null){
                wordTemplate.setTags(item.getTags());
            }
            //填充三级指标
            for(AspectActivity item2:aspectActivityList){
                if(item2.getGid().equals(item.getId())){
                    wordTemplate.setThird(item2.getContent());
                    wordTemplate.setTags(item2.getTags());
                }
            }
            //模板内容不可以为空，预先处理
            //没有父级则跳过
            if(wordTemplate.getFirst() == null || wordTemplate.getFirst().equals("") ){
                continue;
            }
            lt.add(wordTemplate);
        }
        //将数据写到list中供模板填充，需要处理空值
        for(WordTemplate item:lt){
            if( item.getSecond() == null || item.getSecond().equals("") ){
                item.setSecond("空");
            }
            if(item.getThird() == null ||item.getThird().equals("") ){
                item.setThird("空");
            }
            if(item.getTags() == null || item.getTags().equals("") ){
                item.setTags("空");
            }
            if(item.getTeachername() == null || item.getTeachername().equals("") ){
                item.setTeachername("空");
            }
        }
        values.put("values",lt);
        try {
            FreemarkerExportWordUtil.exportWord(response, values, "文档.doc", "template.ftl");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

