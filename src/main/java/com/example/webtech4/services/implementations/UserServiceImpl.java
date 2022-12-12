package com.example.webtech4.services.implementations;

import com.example.webtech4.converters.JSONConverter;
import com.example.webtech4.dao.DAOUser;
import com.example.webtech4.dao.DAOUserHibernate;
import com.example.webtech4.dao.HibernateUtil;
import com.example.webtech4.pojo.Booking;
import com.example.webtech4.pojo.User;
import com.example.webtech4.services.interfaces.UserService;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;

public class UserServiceImpl implements UserService {

    private JSONConverter bookingJSONConverter;
    private DAOUser daoUser;

    public UserServiceImpl() {
        daoUser = new DAOUserHibernate(HibernateUtil.getSessionFactory());
        bookingJSONConverter = new JSONConverter();
    }

    @Override
    public void authorize(HttpServletRequest request, HttpServletResponse response) {
        String rus = (String) request.getAttribute("rus");
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(request.getAttribute("user")!=null) {
            out.println("<html><body>");
            if(rus!=null) out.println("<h1>Вы уже авторизованы! Для начала разлогиньтесь!</h1>");
            else out.println("<h1>You have been already authorized! First of all, logout!</h1>");
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
            if(rus!=null) out.println("<h1>Авторизация успешна!</h1>");
            else out.println("<h1>Authorization complete!</h1>");
            out.println("</body></html>");
        }
    }

    @Override
    public void register(HttpServletRequest request, HttpServletResponse response) {
        String rus = (String) request.getAttribute("rus");
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(request.getAttribute("user")!=null) {
            out.println("<html><body>");
            if(rus!=null) out.println("<h1>Вы уже авторизованы! Для начала разлогиньтесь!</h1>");
            else out.println("<h1>You have been already authorized! First of all, logout!</h1>");
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
            if(rus!=null) out.println("<h1>Регистрация успешна!</h1>");
            else out.println("<h1>Registration complete!</h1>");
            out.println("</body></html>");
        }
        else {
            out.println("<html><body>");
            if(rus!=null) out.println("<h1>Такой пользователь уже есть!</h1>");
            else out.println("<h1>Such user is already exists!</h1>");
            out.println("</body></html>");
        }
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        String rus = (String) request.getAttribute("rus");
        PrintWriter out = null;
        if(request.getAttribute("user")==null) {
            try {
                out = response.getWriter();
                out.println("<html><body>");
                if(rus!=null) out.println("<h1>Вы не авторизованы! Для начала залогиньтесь!</h1>");
                else out.println("<h1>You are not authorized! First of all, log in!!</h1>");
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
            if(rus!=null) out.println("<h1>Вы разлогинены!</h1>");
            else out.println("<h1>You have been logout!</h1>");
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
        return admin!=null && admin.getRole().getName().equals("admin");
    }
}
