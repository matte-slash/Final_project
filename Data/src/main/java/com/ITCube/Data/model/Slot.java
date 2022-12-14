package com.ITCube.Data.model;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Matteo Rosso
 */

@Entity
@Table(name="slot_desk")
public class Slot {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    @Column(name="desk_name", nullable=false)
    private String deskName;
    @Column(name = "type", nullable = false)
    private Type type;

    @Column(name="available")
    private boolean available=true;

    @Column(name="date", nullable = false)
    private LocalDateTime date;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="room_id", nullable= false)
    private Room room;

    public Slot(){}

    public Slot(String deskName, Type type, LocalDateTime date, Room room){
        this.deskName = deskName;
        this.type = type;
        this.date = date;
        this.room = room;
    }

    @Override
    public String toString() {
        return "Slot{" +
                "id=" + id +
                ", deskName='" + deskName + '\'' +
                ", type=" + type +
                ", available=" + available +
                ", date=" + date +
                ", room=" + room +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDeskName() {
        return deskName;
    }

    public void setDeskName(String deskName) {
        this.deskName = deskName;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
