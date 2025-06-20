package com.fiveup.core.teacherworkspace.common.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface ExcelField {

    /**
     * 列名
     * @return String
     */
    String value() default "";
}
