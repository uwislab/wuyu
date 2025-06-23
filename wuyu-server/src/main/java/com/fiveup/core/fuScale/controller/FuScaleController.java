package com.fiveup.core.fuScale.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fiveup.core.common.result.PageResult;
import com.fiveup.core.common.result.Result;
import com.fiveup.core.fuScale.aop.Log;
import com.fiveup.core.fuScale.model.*;
import com.fiveup.core.fuScale.service.FuScaleService;
import com.fiveup.core.management.common.CommonResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/fuScale")
public class FuScaleController {

    @Resource
    private FuScaleService fuScaleService;

    /**
     * 获取领域列表
     *
     * @return List<Domain> 领域列表
     */
    @GetMapping(value = "/getDomainList")
    public CommonResponse getDomainList() {
        List<Domain> domainList = fuScaleService.getDomainList();
        return CommonResponse.ok(domainList);
    }

    /**
     * 新增量表
     *
     * @param scaleInfo
     * @return 0或1
     */
    @PostMapping(value = "/insertFuScale")
    @Log(name = "新增量表")
    public CommonResponse insertFuScale(@RequestBody ScaleInfo scaleInfo) {
//        BasicTeacher basicTeacher = basicTeacherMapper.selectById(scaleInfo.getCreatorId());
//        if(basicTeacher == null){
            scaleInfo.setCreatorId(2018083065);
//        }
        int code = fuScaleService.insertFuScale(scaleInfo);
        return CommonResponse.ok(code);
    }

    /**
     * 插入量表评价内容
     *
     * @param scaleContent
     * @return 0或1
     */
    @PostMapping(value = "/insertScaleContent")
    public CommonResponse insertScaleContent(@RequestBody ScaleContent scaleContent) {
        System.out.println("insert item:");
        System.out.println(scaleContent);
        int code = fuScaleService.insertScaleContent(scaleContent);
        return CommonResponse.ok(code);
    }

    //添加
    @PostMapping(value = "/updateScaleContent")
    public CommonResponse updateScaleContent(@RequestBody ScaleContent scaleContent) {
        System.out.println("update item:");
        System.out.println(scaleContent);
        int code = fuScaleService.updateScaleContent(scaleContent);
        return CommonResponse.ok(code);
    }

    /**
     * 获取最新插入的量表id （？）
     *
     * @return 最新的量表id
     */
    @GetMapping(value = "/getFuScaleId")
    public CommonResponse getFuScaleId() {
        int ScaleId;
        ScaleId = fuScaleService.getFuScaleId();
        return CommonResponse.ok(ScaleId);
    }

    /**
     * 根据量表标题查询量表信息
     *
     * @param title
     * @return 量表信息
     */
    @GetMapping(value = "/getFuScaleByTitle")
    public CommonResponse getFuScaleByTitle(String title) {
        return CommonResponse.ok(fuScaleService.getFuScaleByTitle(title));
    }

    /**
     * 获取所有量表的信息
     *
     * @return scaleList
     */
    @GetMapping(value = "/getAllFuScale")
    public CommonResponse getAllFuScale() {
        List<ScaleInfo> scaleList;
        scaleList = fuScaleService.getAllFuScale();

        return CommonResponse.ok(scaleList);
    }

    /**
     * 获取最新的量表信息 （？）
     *
     * @return scaleContent
     */
    @GetMapping(value = "/getItemContent")
    public CommonResponse getItemContent() {
        ScaleContent scaleContent;
        scaleContent = fuScaleService.getItemContent();
        return CommonResponse.ok(scaleContent);
    }

    /**
     * 根据评价id删除评价内容
     *
     * @param
     * @return 0或1
     */
    @GetMapping(value = "/deleteItemContent")
    public CommonResponse deleteItemContent(int id) {
        System.out.println(id);
        int code = fuScaleService.deleteItemContent(id);
        return CommonResponse.ok(code);
    }

    @GetMapping(value = "/deleteFuScaleContent")
    public CommonResponse deleteFuScaleContent(Integer id) {
        return CommonResponse.ok(fuScaleService.deleteFuScaleContent(id));
    }

//    @GetMapping(value = "/getContentById")
//    public CommonResponse getContentById(@RequestParam Integer scaleId) {
//        List<ScaleContent> scaleContentList;
//        scaleContentList = fuScaleService.getContentById(scaleId);
//
//        return CommonResponse.ok(scaleContentList);
//    }

    /**
     * 根据量表id获取该量表的评价内容
     *
     * @param scaleId
     * @return scaleContentList
     */
    @GetMapping(value = "/getContentByIdCopy")
    public CommonResponse getContentByIdCopy(@RequestParam Integer scaleId) {
        List<ScaleContent> scaleContentList;
        scaleContentList = fuScaleService.getContentByIdCopy(scaleId);
        return CommonResponse.ok(scaleContentList);
    }

    /**
     * 根据量表id获取量表信息
     *
     * @param scaleId
     * @return scaleContentList
     */
    @GetMapping(value = "/getEditableContent")
    public CommonResponse getEditableContent(@RequestParam Integer scaleId) {
        List<ScaleContent> scaleContentList;
        scaleContentList = fuScaleService.getEditableContent(scaleId);
        return CommonResponse.ok(scaleContentList);
    }


//    @GetMapping(value = "/getItemByPre")
//    public CommonResponse getItemByPre(Integer preItemId){
//        return CommonResponse.ok(fuScaleService.getItemByPre(preItemId));
//    }

    /**
     * 根据preItemId获取当前评价内容的上级评价内容
     *
     * @param preItemId
     * @return 上级评价内容
     */
    @GetMapping(value = "/getItemByPreCopy")
    public CommonResponse getItemByPreCopy(Integer preItemId) {
        return CommonResponse.ok(fuScaleService.getItemByPreCopy(preItemId));
    }

    /**
     * 改变量表状态
     *
     * @param scaleInfo
     * @return 0或1
     */
    @GetMapping(value = "/changeState")
    public CommonResponse changeState(ScaleInfo scaleInfo) {
        return CommonResponse.ok(fuScaleService.changeState(scaleInfo));
    }

    /**
     * 根据量表状态查询量表信息
     *
     * @param stateId
     * @return 量表集合
     */
    @GetMapping(value = "/getFuScaleByState")
    public CommonResponse getFuScaleByState(Integer stateId) {
        return CommonResponse.ok(fuScaleService.getScaleBySate(stateId));
    }

    /**
     * 根据创建者名，标题，创建时间查找
     *
     * @param scaleInfo
     * @return List<ScaleInfo>
     */
    @PostMapping(value = "/getBy")
    public CommonResponse getBy(@RequestBody ScaleInfo scaleInfo) {
        List<ScaleInfo> list = fuScaleService.getScaleByTDU(scaleInfo);
        return CommonResponse.ok(list);
    }

    /**
     * 根据评价内容id删除指定评价内容，及其下级的评级内容
     *
     * @param itemId 评级内容id
     * @return 删除结果
     */
    @PostMapping(value = "/deleteItem")
    public CommonResponse delItem(Integer itemId) {
        fuScaleService.deleteItem(itemId);
        return CommonResponse.ok();
    }

    /**
     * 编辑指定评价内容
     *
     * @param scaleContent 评级内容
     * @return
     */
    @PostMapping(value = "/editItem")
    public CommonResponse editItem(@RequestBody ScaleContent scaleContent) {
        int count = fuScaleService.editScaleContent(scaleContent);
        return CommonResponse.ok(count);
    }

    /**
     * 根据量表id删除量表，及其关联的评价内容
     *
     * @param scaleId 量表id
     * @return 删除结果
     */
    @PostMapping(value = "/deleteScale")
    @Log(name = "删除量表")
    public CommonResponse delScale(Integer scaleId) {
        fuScaleService.deleteScale(scaleId);
        return CommonResponse.ok();
    }

    /**
     * 复制量表
     *
     * @param scaleId 量表id
     * @return 复制结果
     */
    @PostMapping(value = "/scaleCopy")
    @Log(name = "复制量表")
    public CommonResponse scaleCopy(Integer scaleId) {
        return fuScaleService.scaleCopy(scaleId);
    }

    /**
     * 根据量表id获取该量表的所有评价内容
     *
     * @param scaleId
     * @return scaleContentList
     */
    @GetMapping(value = "/getContentById")
    public CommonResponse getContentById(@RequestParam Integer scaleId) {
        List<ScaleContent> scaleContentList;
        scaleContentList = fuScaleService.getContentById(scaleId);
        return CommonResponse.ok(scaleContentList);
    }

    /**
     * 修改量表信息
     *
     * @param scaleInfo scaleId:量表id  title：量表标题  grade：绑定年级
     * @return 修改结果
     */
    @PostMapping(value = "/editScale")
    @Log(name = "编辑量表")
    public CommonResponse editScale(@RequestBody ScaleInfo scaleInfo) {
        System.out.println(scaleInfo);
        return fuScaleService.editScale(scaleInfo);
    }

    @GetMapping(value = "/getTreeItemList")
    public CommonResponse getTree(Integer scaleId) {
        return fuScaleService.getTreeItemList(scaleId);
    }
    /**
     * 查询子级的分数和并且查询其父级的分数
     * @param  id //评论项ID
     * @return 子级和父级的分数
     * 返回null说明不存在这一项
     */
    @GetMapping("/SumScores")
    public CommonResponse sumScores(Integer id){
        Map<String,Integer> list=new HashMap<String,Integer>();
        Integer PreSum=null;
        Integer CurSum=null;
        Integer preID=fuScaleService.getContentPreIDbyID(id);
        if(preID == null){
            CurSum=fuScaleService.getScoreById(id);
        }
        else{
            PreSum=fuScaleService.getScoreById(preID);
            CurSum=fuScaleService.getSumOfChildScoreById(preID);
        }
        list.put("PreSum",PreSum);
        list.put("CurSum",CurSum);
        return  CommonResponse.ok(list);
    }

    @GetMapping("/ChildSumScores")
    public CommonResponse childSumScores(Integer id){
        Map<String,Integer> list=new HashMap<String,Integer>();
        Integer ChildSum=null;
        Integer CurSum=null;
        CurSum=fuScaleService.getScoreById(id);
        ChildSum=fuScaleService.getSumOfChildScoreById(id);
        list.put("ChildSum",ChildSum);
        list.put("CurSum",CurSum);
        return  CommonResponse.ok(list);
    }

    @GetMapping("/getExam")
    public CommonResponse getExam(Integer scaleId) {
        List<ScaleContent> exam = fuScaleService.getExam(scaleId);
        return CommonResponse.ok(exam);
    }

    @GetMapping("/getItemList")
    public CommonResponse<String> getItemList(@RequestParam("scaleId") Integer scaleId) {
        String result = null;
        try {
            result = fuScaleService.getItemList(scaleId);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return CommonResponse.ok(result);
    }

    @GetMapping("/getGrade")
    public List<Integer> getGrade(){
        List<Integer> grades=fuScaleService.getGrade();
        return grades;
    }

    @GetMapping(value = "/getFuScaleByStates")
    public CommonResponse getFuScaleByStates(String[] stateIds){
        List<ScaleInfo> scaleInfos=new ArrayList<>();
        List<ScaleInfo> temp;
        for(int stateId=0;stateId<stateIds.length;stateId++){
            temp=fuScaleService.getScaleBySate(Integer.parseInt(stateIds[stateId]));
            scaleInfos.addAll(temp);
        }
        return CommonResponse.ok(scaleInfos);
    }
    @GetMapping("/getAllLevelScore")
    public  CommonResponse getAllLevelScore(Integer scaleId){
        List<ScaleContent> contents = fuScaleService.getContentById(scaleId);
        Map<Integer,Integer> result = new HashMap<>();
        for (ScaleContent content:contents){
            if(result.containsKey(content.getItemLevel())){
                result.put(content.getItemLevel(),result.get(content.getItemLevel())+content.getItemScore());
            }else {
                result.put(content.getItemLevel(),content.getItemScore());
            }

        }
        return CommonResponse.ok(result);
    }
    @GetMapping("/getExecution")
    public  CommonResponse getExecution(Execution execution){
        List<Execution> executions = fuScaleService.getExecution(execution);
        return CommonResponse.ok(executions);
    }
    @PostMapping("/addExecution")
    public CommonResponse addExecution(@RequestBody Execution execution){
        //获取该年级已经绑定的量表
        int isAdd = fuScaleService.isAdd(execution.getScaleId(), execution.getGradeId());
        if(isAdd==1){
            return CommonResponse.fail(1,"该年级在该领域已绑定评价量表");
        }

        return CommonResponse.ok(fuScaleService.addExecution(execution));
    }
    @PostMapping("/delExecution")
    public CommonResponse delExecution(Integer execId){
        return CommonResponse.ok(fuScaleService.delExecution(execId));
    }
}
