package com.fiveup.core.mainpage.domain.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("basic_web_user")
public class WebUser {
    @TableId("id")
    private int id;
    private String username;
    private String password;
    private int identity;
    private Integer gender;
    @TableField("phone_number")
    private String phone;
    private String realName;
    private Integer schoolId;
    private String birthPlace;
    private String politicalAppearance;
    private String position;
    private String title;
    
    /**
     * 用户状态：0正常，1禁用
     */
    private Integer status;
}
