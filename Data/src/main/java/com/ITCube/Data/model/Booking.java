package com.ITCube.Data.model;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Matteo Rosso
 */

@Entity
@Table(name="booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "date_of_booking")
    private LocalDateTime dateOfBooking;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id", nullable= false)
    private User user;

    //private Slot slot;

}
