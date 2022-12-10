package com.example.webtech4.services.implementations;

import com.example.webtech4.converters.JSONConverter;
import com.example.webtech4.dao.DAOUser;
import com.example.webtech4.dao.DAOUserHibernate;
import com.example.webtech4.pojo.Booking;
import com.example.webtech4.pojo.User;
import com.example.webtech4.services.interfaces.UserService;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;

public class UserServiceImpl implements UserService {

    private JSONConverter bookingJSONConverter;
    private DAOUser daoUser;

    public UserServiceImpl() {
        daoUser = new DAOUserHibernate();
        bookingJSONConverter = new JSONConverter();
    }

    @Override
    public void authorize(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(request.getAttribute("user")!=null) {
            out.println("<html><body>");
            out.println("<h1>Вы уже авторизованы! Для начала разлогиньтесь!</h1>");
            out.println("</body></html>");
            return;
        }

        User user;
        try(BufferedReader bufferedReader = request.getReader()) {
            user = bookingJSONConverter.getObjectFromJSON(bufferedReader.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        User dbUser = daoUser.findBoookingByEmail(user.getEmail());
        if(dbUser.getPasswordHash() == user.getPasswordHash()) {
            request.getSession().setAttribute("user",dbUser);
            out.println("<html><body>");
            out.println("<h1>Авторизация успешна!</h1>");
            out.println("</body></html>");
        }
    }

    @Override
    public void register(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(request.getAttribute("user")!=null) {
            out.println("<html><body>");
            out.println("<h1>Вы уже авторизованы! Для начала разлогиньтесь!</h1>");
            out.println("</body></html>");
            return;
        }

        User user;

        try(BufferedReader bufferedReader = request.getReader()) {
            user = bookingJSONConverter.getObjectFromJSON(bufferedReader.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        User dbUser = daoUser.findBoookingByEmail(user.getEmail());
        if(dbUser==null) {
            daoUser.saveUser(user);
            request.getSession().setAttribute("user",user);
            out.println("<html><body>");
            out.println("<h1>Регистрация успешна!</h1>");
            out.println("</body></html>");
        }
        else {
            out.println("<html><body>");
            out.println("<h1>Такой пользователь уже есть!</h1>");
            out.println("</body></html>");
        }
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter out = null;
        if(request.getAttribute("user")==null) {
            try {
                out = response.getWriter();
                out.println("<html><body>");
                out.println("<h1>Вы не авторизованы! Для начала залогиньтесь!</h1>");
                out.println("</body></html>");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }

        request.getSession().invalidate();

        try {
            out = response.getWriter();
            out.println("<html><body>");
            out.println("<h1>Вы разлогинены!</h1>");
            out.println("</body></html>");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isUser(ServletRequest request, ServletResponse response) {
        User user = (User)request.getAttribute("user");
        return user!=null;
    }

    @Override
    public boolean isAdmin(ServletRequest request, ServletResponse response) {
        User admin = (User)request.getAttribute("user");
        return admin.getRole().getName().equals("admin");
    }
}
