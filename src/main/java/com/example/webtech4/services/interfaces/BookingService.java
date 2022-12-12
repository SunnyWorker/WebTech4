package com.example.webtech4.services.interfaces;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface BookingService {
    void book(HttpServletRequest request, HttpServletResponse response);
    void getAllBookings(HttpServletRequest request, HttpServletResponse response);
    void deleteBooking(HttpServletRequest request, HttpServletResponse response);
}
