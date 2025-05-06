package com.fiveup.core.events.model.ext;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
import lombok.Data;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.util.List;

/**
 * @author 钟承远
 * @date 2022/4/27
 */
@Data
@HeadStyle(horizontalAlignment = HorizontalAlignment.CENTER)
@ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
@HeadRowHeight(30)
@ColumnWidth(30)
@ContentRowHeight(25)
public class ActivityStudentDownloadExt {

    @ExcelProperty("活动名称")
    private String activityName;

    @ExcelProperty("学生分数")
    private Float studentScore;

    @ExcelProperty("学生姓名")
    private String studentName;

    @ExcelProperty("活动地点")
    private String activityPlace;

    @ExcelProperty("活动类型")
    private Integer activityType;

    @ExcelProperty("活动量表")
    private Integer activityAspect;

    @ExcelProperty("活动分数")
    private Float activityScore;

    @ExcelProperty("学号")
    private String studentNum;

    @ExcelProperty("性别")
    private Integer gender;

    @ExcelProperty("家长电话")
    private String parentPhoneNum;

    @ExcelProperty("年级")
    private String grade;

    @ExcelProperty("班主任姓名")
    private String monitorName;

    @ExcelProperty("班级Id")
    private Long classId;

    @ExcelProperty("班级名")
    private String className;

    @ExcelProperty("活动Id")
    private Long activityId;

    @ExcelProperty("学生Id")
    private Long studentId;
}
