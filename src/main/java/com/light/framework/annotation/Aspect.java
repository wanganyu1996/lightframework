package com.light.framework.annotation;

import java.lang.annotation.*;

/**
 * Created by wanganyu on 2018/04/13.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
    Class<? extends Annotation> value();
}
