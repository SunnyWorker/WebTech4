package com.example.webtech4.servlets.booking;

import java.io.*;

import com.example.webtech4.services.implementations.BookingServiceImpl;
import com.example.webtech4.services.interfaces.BookingService;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "addBooking", value = "/booking/book")
public class AddBookingServlet extends HttpServlet {
    private BookingService bookingService;

    public AddBookingServlet() {
        bookingService = new BookingServiceImpl();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        bookingService.book(request, response);
    }

}