package com.example.webtech4.servlets.user;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ChangeLanguageServlet", value = "/user/changeLang")
public class ChangeLanguageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String value = request.getParameter("rus");
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(value.equals("true")) {
            out.println("<html><body>");
            out.println("<h1>Язык установлен: русский!</h1>");
            out.println("</body></html>");
            request.setAttribute("rus","true");
        }
        else {
            out.println("<html><body>");
            out.println("<h1>Language set: english!</h1>");
            out.println("</body></html>");
            request.setAttribute("rus",null);
        }
    }

}
