package com.fiveup.core.teacherworkspace.model.bo;

import com.fiveup.core.teacherworkspace.common.annotation.ExcelField;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class LessonFailExportBo extends LessonExcelBo{
    @ExcelField("失败原因")
    private String failReason;
}
