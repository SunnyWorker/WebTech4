package com.example.webtech4.services.interfaces;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface BookingService {
    void book(HttpServletRequest request, HttpServletResponse response);
    void getAllBookings(HttpServletRequest request, HttpServletResponse response);
    void deleteBooking(HttpServletRequest request, HttpServletResponse response);
}
