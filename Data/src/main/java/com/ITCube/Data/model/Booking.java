package com.ITCube.Data.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.Clock;
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

    @JsonIgnore
    private LocalDateTime creationDate;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalDateTime startDate;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalDateTime endDate;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="user_id", nullable= false)
    private User user;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="desk_id", nullable= false)
    private Desk desk;

    public Booking() {
    }

    public Booking(LocalDateTime startDate, LocalDateTime endDate, User user, Desk desk) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.user = user;
        this.desk = desk;
        this.creationDate= LocalDateTime.now();
    }

    public Booking(LocalDateTime creationDate, LocalDateTime startDate, LocalDateTime endDate, User user, Desk desk) {
        this.creationDate = creationDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.user = user;
        this.desk = desk;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", creationDate=" + creationDate +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", user=" + user +
                ", desk=" + desk +
                '}';
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Desk getDesk() {
        return desk;
    }

    public void setDesk(Desk desk) {
        this.desk = desk;
    }
}
