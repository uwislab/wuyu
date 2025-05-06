package com.fiveup.core.analyze.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity //绑定数据库的表
@Data
public class Ana_updata_time {
    @Id //绑定数据库的表的元素
    private Integer id;
    private Date Updatatime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getUpdatatime() {
        return Updatatime;
    }

    public void setUpdatatime(Date updatatime) {
        Updatatime = updatatime;
    }
}
