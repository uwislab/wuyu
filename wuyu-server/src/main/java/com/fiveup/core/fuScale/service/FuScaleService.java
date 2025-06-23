package com.fiveup.core.fuScale.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fiveup.core.common.result.PageResult;
import com.fiveup.core.fuScale.model.*;
import com.fiveup.core.management.common.CommonResponse;

import java.util.List;

public interface FuScaleService {

    List<Domain> getDomainList();

    int insertFuScale(ScaleInfo scaleInfo);

    int insertScaleContent(ScaleContent scaleContent);

    //加的
    int updateScaleContent(ScaleContent scaleContent);

    int editScaleContent(ScaleContent scaleContent);

    int getFuScaleId();

    List<ScaleInfo> getFuScaleByTitle(String title);

    List<ScaleInfo> getAllFuScale();

    ScaleContent getItemContent();

    int deleteItemContent(int id);

    List<ScaleContent> getEditableContent(Integer scaleId);

    Integer changeState(ScaleInfo scaleInfo);

    List<ScaleInfo> getScaleBySate(Integer stateId);

    Integer deleteFuScaleContent(Integer id);

    List<ScaleContent> getContentByIdCopy(Integer scaleId);

    List<ScaleContent> getItemByPreCopy(Integer preItemId);

    List<ScaleInfo> getScaleByTDU(ScaleInfo scaleInfo);

    Integer deleteItem(Integer itemId);

    Integer deleteScale(Integer scaleId);

    CommonResponse scaleCopy(Integer scaleId);

    List<ScaleContent> getContentById(Integer scaleId);

    CommonResponse editScale(ScaleInfo scaleInfo);

    CommonResponse getTreeItemList(Integer scaleId);

    Integer getContentPreIDbyID(Integer id);

    Integer getSumOfChildScoreById(Integer id);

    Integer getScoreById(Integer id);

    Integer copyScaleContent(ScaleContent scaleContent);

    List<Integer> getGrade();

    List<ScaleContent> getExam(Integer scaleId);

    String getItemList(Integer scaleId) throws JsonProcessingException;

    List<Execution> getExecution(Execution execution);

    int addExecution(Execution execution);

    ScaleInfo getFuScaleById(Integer scaleId);

    int delExecution(Integer execId);
    int isAdd(Integer scaleId , Integer gradeId);
}
