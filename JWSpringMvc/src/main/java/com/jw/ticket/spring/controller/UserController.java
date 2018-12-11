package com.jw.ticket.spring.controller;

import com.jw.ticket.spring.annotation.JWAutowired;
import com.jw.ticket.spring.annotation.JWController;
import com.jw.ticket.spring.annotation.JWRequestMapping;
import com.jw.ticket.spring.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author: chenJenWen
 * @Date: 2018/11/2 15:17
 * @email: 643969814@qq.com
 * @Version 1.0
 */
@JWController
@JWRequestMapping("/api")
public class UserController {

    @JWAutowired
    UserService userService;

    @JWRequestMapping("/user")
    public void getUser(HttpServletRequest request, HttpServletResponse resp, String name, String age) {
        PrintWriter writer = null;
        try {
            writer = resp.getWriter();
            String user = userService.getUser(name, age);
            writer.write(user);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            writer.close();
        }

    }

}
