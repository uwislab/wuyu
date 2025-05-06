package com.fiveup.core.questionnaire.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ActivityVO {

    @ApiModelProperty(value = "活动名称")
    private String activityName;

    @ApiModelProperty(value = "活动编号")
    private String activityId;

    @ApiModelProperty(value = "活动学期")
    private String activityTerm;

    @ApiModelProperty(value = "活动类别")
    private String activityTarget;

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getActivityTerm() {
        return activityTerm;
    }

    public void setActivityTerm(String activityTerm) {
        this.activityTerm = activityTerm;
    }

    public String getActivityTarget() {
        return activityTarget;
    }

    public void setActivityTarget(String activityTarget) {
        this.activityTarget = activityTarget;
    }
}
