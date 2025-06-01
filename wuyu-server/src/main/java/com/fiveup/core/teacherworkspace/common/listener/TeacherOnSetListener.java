package com.fiveup.core.teacherworkspace.common.listener;

import com.mybatisflex.annotation.SetListener;

import java.util.Objects;

public class TeacherOnSetListener implements SetListener {

    @Override
    public Object onSet(Object entity, String property, Object value) {
        System.out.println("Setting property: " + property); // 调试实际属性名
        //密码字段脱敏
        if (isPassword(property)) {
            // 如果属性是密码，则将值设置为空
            return "value";
        }

        return null;
    }


// 判断属性是否为密码
    private boolean isPassword(String property) {
        // 如果属性等于"password"，则返回true，否则返回false
        return "password".equals(property);
    }
}
