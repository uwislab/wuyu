package com.fiveup.core.teacherworkspace.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PageLessonDto extends PageDto {
    @ApiModelProperty("最低年级")
    private Integer minGrade;
    @ApiModelProperty("最高年级")
    private Integer maxGrade;
    @ApiModelProperty("班级")
    private Integer classNum;
    @ApiModelProperty("课程名称")
    private String course;
    @ApiModelProperty("学年")
    private String academicYear;
    @ApiModelProperty("学期")
    private Integer semester;
}
