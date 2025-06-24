package com.fiveup.core.webUser.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
@ToString
@Data//代替所有set get
@TableName(value = "basic_web_user")
public class webUser implements Serializable {
    private static final  long serialVersionUID=1L;
    private int id;
    private String username;
    private String password;
    private int identity;
    private int gender;
    private String phoneNumber;
    private String realName;
    private Long schoolId;
}
