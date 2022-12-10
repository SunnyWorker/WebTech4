package com.example.webtech4.services.implementations;

import com.example.webtech4.converters.JSONConverter;
import com.example.webtech4.dao.DAOBooking;
import com.example.webtech4.dao.DAOBookingHibernate;
import com.example.webtech4.pojo.Booking;
import com.example.webtech4.pojo.HotelRoom;
import com.example.webtech4.pojo.User;
import com.example.webtech4.services.interfaces.BookingService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class BookingServiceImpl implements BookingService {
    private JSONConverter bookingJSONConverter;
    private DAOBooking daoBooking;

    public BookingServiceImpl() {
        daoBooking = new DAOBookingHibernate();
        bookingJSONConverter = new JSONConverter();
    }

    @Override
    public void book(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getAttribute("user");
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Booking booking;
        try(BufferedReader bufferedReader = request.getReader()) {
            booking = bookingJSONConverter.getObjectFromJSON(bufferedReader.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        booking.setUser(user);
        if(daoBooking.isHotelRoomFree(booking)) {
            daoBooking.saveBooking(booking);
            out.println("<html><body>");
            out.println("<h1>Номер забронирован успешно!</h1>");
            out.println("</body></html>");
        }
        else {
            out.println("<html><body>");
            out.println("<h1>Этот номер занят в это время!</h1>");
            out.println("</body></html>");
        }
    }

    @Override
    public void getAllBookings(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        out.println(bookingJSONConverter.convertToJSON(daoBooking.findAllBookings()));
    }

    @Override
    public void deleteBooking(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getAttribute("user");
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Booking booking;
        try(BufferedReader bufferedReader = request.getReader()) {
            booking = bookingJSONConverter.getObjectFromJSON(bufferedReader.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Booking dbBooking = daoBooking.findBoookingById(booking.getId());
        if(dbBooking==null) {
            out.println("<html><body>");
            out.println("<h1>Такой брони нет!</h1>");
            out.println("</body></html>");
        }
        else if(dbBooking.getUser().equals(user) || user.getRole().getName().equals("admin")) {
            daoBooking.deleteBooking(dbBooking);
            out.println("<html><body>");
            out.println("<h1>Бронь успешно удалена!</h1>");
            out.println("</body></html>");
        }
        else {
            out.println("<html><body>");
            out.println("<h1>У вас недостаточно прав для удаления данной брони!</h1>");
            out.println("</body></html>");
        }

    }
}
