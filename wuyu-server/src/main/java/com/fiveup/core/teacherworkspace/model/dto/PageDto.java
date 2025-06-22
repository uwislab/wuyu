package com.fiveup.core.teacherworkspace.model.dto;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDto {
    @NotNull
    @ApiModelProperty(value = "当前页码")
    private Long page;
    @NotNull
    @ApiModelProperty(value = "每页数量")
    private Long size;
}
