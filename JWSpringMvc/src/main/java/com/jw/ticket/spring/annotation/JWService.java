package com.jw.ticket.spring.annotation;

import java.lang.annotation.*;

/**
 * @Author: chenJenWen
 * @Date: 2018/11/1 17:59
 * @email: 643969814@qq.com
 * @Version 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JWService {
    /**
     * 表示给service定义的名称
     *
     * @return
     */
    String value() default "";
}
