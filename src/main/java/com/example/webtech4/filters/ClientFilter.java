package com.example.webtech4.filters;

import com.example.webtech4.services.implementations.UserServiceImpl;
import com.example.webtech4.services.interfaces.UserService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(filterName = "ClientFilter")
public class ClientFilter implements Filter {

    private UserService userService;

    public ClientFilter() {
        userService = new UserServiceImpl();
    }

    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        if(userService.isUser(request,response)) chain.doFilter(request, response);
        else {
            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            out.println("<h1>Вы не авторизованы, доступ запрещён!</h1>");
            out.println("</body></html>");
        }
    }
}
