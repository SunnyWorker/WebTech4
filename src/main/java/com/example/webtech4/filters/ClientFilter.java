package com.example.webtech4.filters;

import com.example.webtech4.services.implementations.UserServiceImpl;
import com.example.webtech4.services.interfaces.UserService;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
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
        String rus = (String) request.getAttribute("rus");
        if(userService.isUser(request,response)) chain.doFilter(request, response);
        else {
            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            if(rus!=null) out.println("<h1>Вы не авторизованы, доступ запрещён!</h1>");
            else out.println("<h1>You are not authorized, access denied!</h1>");
            out.println("</body></html>");
        }
    }
}
