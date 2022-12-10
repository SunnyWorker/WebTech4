package com.example.webtech4.servlets.booking;

import com.example.webtech4.services.implementations.BookingServiceImpl;
import com.example.webtech4.services.interfaces.BookingService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "deleteBooking", value = "/booking/deleteBook")
public class DeleteBookingServlet extends HttpServlet {

    private BookingService bookingService;

    public DeleteBookingServlet() {
        bookingService = new BookingServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        bookingService.deleteBooking(request, response);
    }
}
