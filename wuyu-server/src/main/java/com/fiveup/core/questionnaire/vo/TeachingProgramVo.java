package com.fiveup.core.questionnaire.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TeachingProgramVo {

    @ApiModelProperty(value = "教案名称")
    private String title;

    @ApiModelProperty(value = "教案编号")
    private String code;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
