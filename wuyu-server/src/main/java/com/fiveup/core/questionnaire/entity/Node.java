package com.fiveup.core.questionnaire.entity;


import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(description = "活动的思维导图的节点")
public class  Node<T> {

    private T id;
    private int parent;

}
