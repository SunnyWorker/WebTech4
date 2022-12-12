package com.example.webtech4.servlets.booking;

import java.io.*;

import com.example.webtech4.services.implementations.BookingServiceImpl;
import com.example.webtech4.services.interfaces.BookingService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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