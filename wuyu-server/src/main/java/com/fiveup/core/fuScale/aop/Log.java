package com.fiveup.core.fuScale.aop;


import java.lang.annotation.*;

@Target({ElementType.PARAMETER , ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    String name() default "";
}
