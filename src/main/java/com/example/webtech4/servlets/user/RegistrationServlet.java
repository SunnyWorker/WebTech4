package com.example.webtech4.servlets.user;

import com.example.webtech4.services.implementations.UserServiceImpl;
import com.example.webtech4.services.interfaces.UserService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "RegistrationServlet", value = "/user/register")
public class RegistrationServlet extends HttpServlet {

    private UserService userService;

    public RegistrationServlet() {
        userService = new UserServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        userService.register(request,response);
    }
}
