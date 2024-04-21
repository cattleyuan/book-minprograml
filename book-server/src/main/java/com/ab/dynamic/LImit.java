package com.ab.dynamic;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface LImit {
    int count() default 3;

    long period() default 1;

}
