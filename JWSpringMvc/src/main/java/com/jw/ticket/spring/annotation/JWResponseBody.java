package com.jw.ticket.spring.annotation;

import java.lang.annotation.*;

/**
 * @Author: chenJenWen
 * @Date: 2018/11/1 18:03
 * @email: 643969814@qq.com
 * @Version 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JWResponseBody {
}
