package com.jw.ticket.spring.annotation;

import java.lang.annotation.*;

/**
 * @Author: chenJenWen
 * @Date: 2018/11/2 10:16
 * @email: 643969814@qq.com
 * @Version 1.0
 */
@Target(ElementType.PARAMETER)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface JWRequestParam {
    /**
     * 参数的别名
     *
     * @return
     */
    String value();
}
