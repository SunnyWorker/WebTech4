package com.example.webtech4.dao;

import com.example.webtech4.pojo.Booking;

import java.util.List;
import java.util.Optional;

public interface DAOBooking {
    Optional<Booking> findBoookingById(long id);
    List<Booking> findAllBookings();
    void saveBooking(Booking booking);
    void deleteBooking(Booking booking);

}
