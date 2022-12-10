package com.example.webtech4.dao;

import com.example.webtech4.pojo.Booking;
import com.example.webtech4.pojo.HotelRoom;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public class DAOBookingHibernate implements DAOBooking {

    private SessionFactory sessionFactory;

    public DAOBookingHibernate(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public Booking findBoookingById(long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Booking booking;
        try {
            transaction = session.beginTransaction();
            booking = session.get(Booking.class,id);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction!=null) transaction.rollback();
            return null;
        } finally {
            session.close();
        }
        return booking;
    }

    @Override
    public boolean isHotelRoomFree(Booking booking) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Booking> cr = criteriaBuilder.createQuery(Booking.class);
        Root<Booking> root = cr.from(Booking.class);
        cr.select(root);
        cr.where(criteriaBuilder.and(criteriaBuilder.equal(root.get("hotelRoom_id"),booking.getHotelRoom().getId())),
                criteriaBuilder.not(criteriaBuilder.or(
                        criteriaBuilder.and(
                                criteriaBuilder.greaterThan(root.get("deoccupationTime"),booking.getOccupationTime()),
                                criteriaBuilder.greaterThan(root.get("deoccupationTime"),booking.getDeoccupationTime())
                        ),
                        criteriaBuilder.and(
                                criteriaBuilder.lessThan(root.get("occupationTime"),booking.getOccupationTime()),
                                criteriaBuilder.lessThan(root.get("occupationTime"),booking.getDeoccupationTime()))
                )));
        List<Booking> bookings = null;
        try {
            tx = session.beginTransaction();
            bookings = session.createQuery(cr).getResultList();
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return bookings.size()==0;
    }


    @Override
    public List<Booking> findAllBookings() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Booking> cr = criteriaBuilder.createQuery(Booking.class);
        Root<Booking> root = cr.from(Booking.class);
        cr.select(root);
        List<Booking> bookings = null;
        try {
            transaction = session.beginTransaction();
            bookings = session.createQuery(cr).getResultList();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction!=null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return bookings;
    }

    @Override
    public void saveBooking(Booking booking) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            booking = session.merge(booking);
            session.persist(booking);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction!=null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void deleteBooking(Booking booking) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            booking = session.merge(booking);
            session.remove(booking);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction!=null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
