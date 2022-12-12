package com.example.webtech4.servlets.user;

import com.example.webtech4.services.implementations.UserServiceImpl;
import com.example.webtech4.services.interfaces.UserService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AuthorizationServlet", value = "/user/authorize")
public class AuthorizationServlet extends HttpServlet {

    private UserService userService;

    public AuthorizationServlet() {
        userService = new UserServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        userService.authorize(request, response);
    }
}
