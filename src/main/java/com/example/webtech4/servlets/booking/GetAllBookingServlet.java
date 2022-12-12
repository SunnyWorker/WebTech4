package com.example.webtech4.servlets.booking;

import com.example.webtech4.services.implementations.BookingServiceImpl;
import com.example.webtech4.services.implementations.UserServiceImpl;
import com.example.webtech4.services.interfaces.BookingService;
import com.example.webtech4.services.interfaces.UserService;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "GetAllBookingServlet", value = "/booking/getAllBookings")
public class GetAllBookingServlet extends HttpServlet {

    private BookingService bookingService = new BookingServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        bookingService.getAllBookings(request, response);
    }
}
