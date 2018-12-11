package com.jw.ticket.spring.annotation;

import java.lang.annotation.*;

/**
 * @Author: chenJenWen
 * @Date: 2018/11/1 17:57
 * @email: 643969814@qq.com
 * @Version 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface JWController {
    /**
     * 表示给controller注册的别名
     *
     * @return
     */
    String value() default "";
}
