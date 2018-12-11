package com.jw.ticket.spring.service.impl;

import com.jw.ticket.spring.annotation.JWService;
import com.jw.ticket.spring.service.UserService;

/**
 * @Author: chenJenWen
 * @Date: 2018/11/2 15:17
 * @email: 643969814@qq.com
 * @Version 1.0
 */
@JWService
public class UserServiceImpl implements UserService {
    @Override
    public String getUser(String name, String age) {
        return "您好！" + name + ",您的年龄" + age + "!";
    }
}
