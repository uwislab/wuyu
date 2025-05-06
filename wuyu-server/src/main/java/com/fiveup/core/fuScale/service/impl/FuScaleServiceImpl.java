package com.fiveup.core.fuScale.service.impl;


import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fiveup.core.common.result.PageResult;
import com.fiveup.core.fuScale.mapper.FuScaleMapper;
import com.fiveup.core.fuScale.model.*;
import com.fiveup.core.fuScale.service.FuScaleService;
import com.fiveup.core.management.common.CommonResponse;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @ClassName: ParentServiceImpl
 * @Author: shilin
 * @Date: 2022/9/18 16:32
 */
@Service
public class FuScaleServiceImpl implements FuScaleService {

    @Autowired
    private FuScaleMapper fuScaleMapper;

    @Override
    public List<Domain> getDomainList() {
        List<Domain> domainList;
        domainList = fuScaleMapper.getDomainList();
        return domainList;
    }

    //新增表头并返回自增id
    @Override
    public int insertFuScale(ScaleInfo scaleInfo) {
        fuScaleMapper.insertFuScale(scaleInfo);
        return scaleInfo.getScaleId();
    }

    @Override
    public int insertScaleContent(ScaleContent scaleContent) {
        return fuScaleMapper.insertScaleContent(scaleContent);
    }

    @Override
    @Transactional
    public int editScaleContent(ScaleContent scaleContent) {
        int count = fuScaleMapper.updateScaleContent(scaleContent);
        if (count != 0) {
            fuScaleMapper.updateItemByPre(scaleContent);
            return 1;
        }
        return 0;
    }

    @Override
    public int getFuScaleId() {
        return fuScaleMapper.getFuScaleId();
    }

    @Override
    public List<ScaleInfo> getFuScaleByTitle(String title) {
        return fuScaleMapper.getFuScaleByTitle(title);
    }

    @Override
    public List<ScaleInfo> getAllFuScale() {
        List<ScaleInfo> list = fuScaleMapper.scaleList();

        return list;
    }


    @Override
    public ScaleContent getItemContent() {
        return fuScaleMapper.getItemContent();
    }

    @Override
    public int deleteItemContent(int id) {
        return fuScaleMapper.deleteItemContent(id);
    }

    @Override
    public List<ScaleContent> getEditableContent(Integer scaleId) {
        return fuScaleMapper.getEditableContent(scaleId);
    }

    @Override
    public Integer changeState(ScaleInfo scaleInfo) {
        return fuScaleMapper.changeState(scaleInfo);
    }

    @Override
    public List<ScaleInfo> getScaleBySate(Integer stateId) {
        List<ScaleInfo> list = fuScaleMapper.getScaleByState(stateId);
        list.forEach(s -> s.setCreatorName(fuScaleMapper.getCreatorName(s.getCreatorId())));
        return list;
    }

    @Override
    public Integer deleteFuScaleContent(Integer id) {
        fuScaleMapper.deleteFuItemContent(id);
        return fuScaleMapper.deleteFuScaleContent(id);
    }

    @Override
    public List<ScaleContent> getContentByIdCopy(Integer scaleId) {
        List<ScaleContent> list = fuScaleMapper.getContentByIdCopy(scaleId);
        list.forEach(s -> s.setHasChildren(fuScaleMapper.hasChildrenForItem(s.getItemId()) > 0));
        return list;
    }

    @Override
    public List<ScaleContent> getItemByPreCopy(Integer preItemId) {
        List<ScaleContent> list = fuScaleMapper.getItemByPreCopy(preItemId);
        list.forEach(s -> s.setHasChildren(fuScaleMapper.hasChildrenForItem(s.getItemId()) > 0));
        return list;
    }

    public List<ScaleInfo> getScaleByTDU(ScaleInfo scaleInfo) {
        return fuScaleMapper.getScalesByCondition(scaleInfo);
    }

    @Override
    public Integer deleteItem(Integer itemId) {
        Set<Integer> lowLevelId = fuScaleMapper.getLowLevelId(itemId);
        lowLevelId.add(itemId);
        fuScaleMapper.deleteItem(lowLevelId);
        return null;
    }

    @Override
    @Transactional
    public Integer deleteScale(Integer scaleId) {
        fuScaleMapper.deleteScale(scaleId);
        fuScaleMapper.deleteAllItemByScaleId(scaleId);
        return null;
    }

    @Override
    @Transactional
    public CommonResponse scaleCopy(Integer scaleId) {
        // 复制量表
        ScaleInfo fuScaleById = fuScaleMapper.getFuScaleById(scaleId);
        if (Objects.isNull(fuScaleById)) {
            return CommonResponse.fail(1024, "复制的量表已经不存在");
        }
        fuScaleById.setTitle(fuScaleById.getTitle() + "-副本");
        fuScaleById.setCreateDate(DateUtil.now());

        fuScaleMapper.insertFuScale(fuScaleById);
        int fuScaleId = fuScaleMapper.getFuScaleId();

        // 复制指标
        List<ScaleContent> scaleContentList = fuScaleMapper.getContentById(scaleId);
        scaleContentList.forEach(scaleContent -> {
            scaleContent.setScaleId(fuScaleId);
            // 如果不是一级指标，需要更改上级id
            if (scaleContent.getItemLevel() > 1) {
                List<ScaleContent> newPreScaleContentList = fuScaleMapper.getNewScaleContentByPre(fuScaleId, scaleContent.getPreItemId());
                ScaleContent preScaleContent = newPreScaleContentList.get(0);
                scaleContent.setPreItem(preScaleContent.getItemContent());
                scaleContent.setPreItemId(preScaleContent.getId());
            }
            fuScaleMapper.insertScaleContent(scaleContent);
        });
        return CommonResponse.ok();
    }

    /**
     * 通过量表id获取执行情况
     *
     * @param scaleId
     * @return
     */
    @Override
    public List<ScaleContent> getContentById(Integer scaleId) {
        List<ScaleContent> list = fuScaleMapper.getContentById(scaleId);
        list.forEach(s -> s.setHasChildren(fuScaleMapper.hasChildrenForItem(s.getItemId()) > 0));
        return list;
    }

    @Override
    public CommonResponse editScale(ScaleInfo scaleInfo) {
        fuScaleMapper.updateScale(scaleInfo);
        return CommonResponse.ok();
    }

    @Override
    public CommonResponse getTreeItemList(Integer scaleId) {
        List<ScaleContent> list = fuScaleMapper.getContentByIdCopy(scaleId);
        list.forEach(item1 -> {
            item1.setChildren(fuScaleMapper.getItemByPreCopy(item1.getItemId()));
            if (item1.getChildren() != null && item1.getChildren().size() > 0) {
                item1.getChildren().forEach(item2 -> {
                    item2.setChildren(fuScaleMapper.getItemByPreCopy(item2.getItemId()));
                });
            }
        });
        return CommonResponse.ok(list);
    }

    @Override
    public Integer getContentPreIDbyID(Integer id) {
        Integer preId=fuScaleMapper.getContentPreIDbyID(id);
        return preId;
    }

    @Override
    public Integer getSumOfChildScoreById(Integer id) {
        Integer score=fuScaleMapper.getSumOfChildScoreById(id);
        return score;
    }

    @Override
    public Integer getScoreById(Integer id) {
        Integer score=fuScaleMapper.getScoreById(id);
        return score;
    }

    @Override
    public Integer copyScaleContent(ScaleContent scaleContent) {
        fuScaleMapper.copyScaleContent(scaleContent);
        Integer itemid=scaleContent.getScaleId();
        return itemid;
    }

    @Override
    public List<Integer> getGrade(){return fuScaleMapper.getGrade();}

    @Override
    public List<ScaleContent> getExam(Integer scaleId) {
        return fuScaleMapper.getExam(scaleId);
    }

    @Override
    public String getItemList(Integer scaleId) throws JsonProcessingException {
        List<ScaleContent> list1 = fuScaleMapper.getContentByIdCopy(scaleId);
        List<ScaleContent> list2 = fuScaleMapper.getContentByIdCopy2(scaleId);
        List<ScaleContent> list3 = fuScaleMapper.getContentByIdCopy3(scaleId);

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode selectOption1Node = buildJson(list1, mapper, "一");
        ObjectNode selectOption2Node = buildJson(list2, mapper, "二");
        ObjectNode selectOption3Node = buildJson(list3, mapper, "三");

        ArrayNode selectOptionsNode = mapper.createArrayNode();
        selectOptionsNode.add(selectOption1Node);
        selectOptionsNode.add(selectOption2Node);
        selectOptionsNode.add(selectOption3Node);

        return mapper.writeValueAsString(selectOptionsNode);
    }

    public ObjectNode buildJson(List<ScaleContent> list, ObjectMapper mapper, String str){
        ObjectNode selectOptionNode = mapper.createObjectNode();
        selectOptionNode.put("label", "参考"+ str +"级指标");
        ArrayNode optionsNode = mapper.createArrayNode();
        list.forEach(item -> {
            ObjectNode optionNode = mapper.createObjectNode();
            optionNode.put("value", item.getItemContent());
            optionNode.put("label", item.getItemContent());
            optionsNode.add(optionNode);
        });
        selectOptionNode.set("options", optionsNode);

        return selectOptionNode;
    }
    @Override
    public List<Execution> getExecution(Execution execution){
        return fuScaleMapper.getExecution(execution);
    }
    @Override
    public int addExecution(Execution execution){
        return fuScaleMapper.addExecution(execution);
    }
    @Override
    public ScaleInfo getFuScaleById(Integer scaleId){
        return fuScaleMapper.getFuScaleById(scaleId);
    }

    @Override
    public int delExecution(Integer execId) {
        return fuScaleMapper.delExecution(execId);
    }

    public int isAdd(Integer scaleId, Integer gradeId){
        return fuScaleMapper.isAdd(scaleId,gradeId);

    }
}
