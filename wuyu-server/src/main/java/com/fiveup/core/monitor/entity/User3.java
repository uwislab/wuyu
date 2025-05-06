package com.fiveup.core.monitor.entity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
@TableName("user3")
@Data
public class User3 {

    @TableId("ip")
    private String ip;

    private String Cpu;

    private String Memtoatal;

    private String Memused;

    private String Memfree;

    private String Memsave;

    private String Swaptotal;

    private String Swapused;

    private String Swapfree;

    private String Trueip;

    private String Date;

}