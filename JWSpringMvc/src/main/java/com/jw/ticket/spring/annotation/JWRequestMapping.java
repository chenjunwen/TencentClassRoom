package com.jw.ticket.spring.annotation;

import java.lang.annotation.*;

/**
 * @Author: chenJenWen
 * @Date: 2018/11/1 18:01
 * @email: 643969814@qq.com
 * @Version 1.0
 * 路径匹配
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JWRequestMapping {
    /**
     * 表示访问该方法的url
     *
     * @return
     */
    String value() default "";
}
