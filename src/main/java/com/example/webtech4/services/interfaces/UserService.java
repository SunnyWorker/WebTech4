package com.example.webtech4.services.interfaces;

import com.example.webtech4.pojo.User;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {
    void authorize(HttpServletRequest request, HttpServletResponse response);
    void register(HttpServletRequest request, HttpServletResponse response);
    void logout(HttpServletRequest request, HttpServletResponse response);

    boolean isUser(ServletRequest request, ServletResponse response);
    boolean isAdmin(ServletRequest request, ServletResponse response);
}
