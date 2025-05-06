package com.fiveup.core.questionnaire.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    /*
    * mp 执行添加操作的时候，这个方法执行
    * */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
//        this.setFieldValByName("version", 1, metaObject);
        this.setFieldValByName("isDeleted", 0, metaObject);//默认0，不删除 1就是删除
    }

    /*
     *
     * mp 执行修改操作的时候，这个方法执行
     * */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }
}
