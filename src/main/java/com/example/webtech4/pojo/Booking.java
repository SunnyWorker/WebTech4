package com.example.webtech4.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "hotelRoom_id", nullable = false)
    private HotelRoom hotelRoom;
    @Column(nullable = false)
    private Timestamp occupationTime;
    @Column(nullable = false)
    private Timestamp deoccupationTime;
}
