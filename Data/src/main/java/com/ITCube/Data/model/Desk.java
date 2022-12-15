package com.ITCube.Data.model;

import javax.persistence.*;

/**
 * @author Matteo Rosso
 */

@Entity
@Table(name="desk")
public class Desk {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    @Column(name="desk_name", nullable=false)
    private String deskName;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="room_id", nullable= false)
    private Room room;

    public Desk() {
    }

    public Desk(String deskName, Room room) {
        this.deskName = deskName;
        this.room = room;
    }

    @Override
    public String toString() {
        return "Desk{" +
                "id=" + id +
                ", deskName='" + deskName + '\'' +
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

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
