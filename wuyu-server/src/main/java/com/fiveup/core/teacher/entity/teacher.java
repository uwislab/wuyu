package com.fiveup.core.teacher.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
@ToString
@Data//代替所有set get
@TableName(value = "basic_teacher")
public class teacher implements Serializable {
    private static final  long serialVersionUID=1L;
    
    @TableId(type = IdType.AUTO)
    private int id;
    private String teacherName;
    private int gender;
    private String phoneNum;
    private String position;
    private String title;
    private String role;
    
    @TableLogic(value = "0", delval = "1")
    private int deleted;
    
    private int schoolId;
    private String username;
    private String password;
    private String politicalAppearance;
    private String birthPlace;
    private int age;
    private String info;


    public String getPhoneNum(teacher teacher) {
        return teacher.getPhoneNum();
    }
}
