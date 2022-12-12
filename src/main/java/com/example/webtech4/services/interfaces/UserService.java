package com.example.webtech4.services.interfaces;

import com.example.webtech4.pojo.User;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface UserService {
    void authorize(HttpServletRequest request, HttpServletResponse response);
    void register(HttpServletRequest request, HttpServletResponse response);
    void logout(HttpServletRequest request, HttpServletResponse response);

    boolean isUser(ServletRequest request, ServletResponse response);
    boolean isAdmin(ServletRequest request, ServletResponse response);
}
