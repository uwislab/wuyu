package com.fiveup.core.questionnaire.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "教师创建的活动")
@TableName("activity")
public class Activity {

    @ApiModelProperty(value = "活动id")
    @TableId(type = IdType.ASSIGN_ID)
    private String activityId;

    @ApiModelProperty(value = "活动名字")
    @TableField("activity_name")
    private String activityName;

    @ApiModelProperty(value = "活动内容")
    @TableField("activity_content")
    private String activityContent;

    @ApiModelProperty(value = "活动学期")
    @TableField("activity_term")
    private int activityTerm;

    @ApiModelProperty(value = "活动类别")
    @TableField("activity_target")
    private String activityTarget;

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityContent() {
        return activityContent;
    }

    public void setActivityContent(String activityContent) {
        this.activityContent = activityContent;
    }

    public int getActivityTerm() {
        return activityTerm;
    }

    public void setActivityTerm(int activityTerm) {
        this.activityTerm = activityTerm;
    }

    public String getActivityTarget() {
        return activityTarget;
    }

    public void setActivityTarget(String activityTarget) {
        this.activityTarget = activityTarget;
    }
}
