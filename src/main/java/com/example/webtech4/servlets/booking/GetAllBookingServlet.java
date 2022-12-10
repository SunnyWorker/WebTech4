package com.example.webtech4.servlets.booking;

import com.example.webtech4.services.implementations.BookingServiceImpl;
import com.example.webtech4.services.implementations.UserServiceImpl;
import com.example.webtech4.services.interfaces.BookingService;
import com.example.webtech4.services.interfaces.UserService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "GetAllBookingServlet", value = "/booking/getAllBookings")
public class GetAllBookingServlet extends HttpServlet {

    private BookingService bookingService;

    public GetAllBookingServlet() {
        bookingService = new BookingServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        bookingService.getAllBookings(request, response);
    }
}
