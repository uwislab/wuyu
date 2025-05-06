package com.fiveup.core.events.model.ext;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

/**
 * @author 钟承远
 * @date 2022/4/29
 */
@Data
@HeadStyle(horizontalAlignment = HorizontalAlignment.CENTER)
@ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
@HeadRowHeight(30)
@ColumnWidth(30)
@ContentRowHeight(25)
public class ActivityStudentScoreTemplateExt {


    @ExcelProperty("活动Id")
    private Long activityId;

    @ExcelProperty("活动名称")
    private String activityName;

    @ExcelProperty("活动类型")
    private String type;

    @ExcelProperty("学生Id")
    private Long studentId;

    @ExcelProperty("学号")
    private String studentNum;

    @ExcelProperty("学生姓名")
    private String studentName;

    @ExcelProperty("学生性别")
    private Integer gender;

    @ExcelProperty("studentScore")
    private Float studentScore;
}
