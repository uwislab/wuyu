package com.fiveup.core.management.model.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

/**
 * @author xxy
 * @date 2024/4/17
 */

@Data
@HeadStyle(horizontalAlignment = HorizontalAlignment.CENTER)
@ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER)
@HeadRowHeight(30)
@ColumnWidth(30)
@ContentRowHeight(25)
public class TiyuscoreExt {

    @ExcelProperty("学号")
    private String studentId;

    @ExcelProperty("学生姓名")
    private String studentName;


    @ExcelProperty("体育道德")
    private Float sportsMorality;

    @ExcelProperty("体育健康知识")
    private Float healthKnowledge;

    @ExcelProperty("心理健康")
    private Float mentalHealth;

    @ExcelProperty("运动技能")
    private Float sportsSkills;

    @ExcelProperty("体育活动")
    private Float sportsActivity;



}
