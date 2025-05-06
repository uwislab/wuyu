package com.fiveup.core.monitor.entity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
@TableName("user2")
@Data
public class User2 {

    @TableId("ip")
    private String ip;

    private String username;

    private String password;

}