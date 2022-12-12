package com.example.webtech4.servlets.user;

import com.example.webtech4.services.implementations.UserServiceImpl;
import com.example.webtech4.services.interfaces.UserService;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LogoutServlet", value = "/user/logout")
public class LogoutServlet extends HttpServlet {

    private UserService userService;

    public LogoutServlet() {
        userService = new UserServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        userService.logout(request,response);
    }
}
