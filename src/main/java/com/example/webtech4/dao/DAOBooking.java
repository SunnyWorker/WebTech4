package com.example.webtech4.dao;

import com.example.webtech4.pojo.Booking;
import com.example.webtech4.pojo.HotelRoom;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface DAOBooking {
    Booking findBoookingById(long id);
    boolean isHotelRoomFree(Booking booking);
    List<Booking> findAllBookings();
    void saveBooking(Booking booking);
    void deleteBooking(Booking booking);

}
