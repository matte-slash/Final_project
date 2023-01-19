package com.ITCube.Data.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Matteo Rosso
 */

@Entity
@Table(name="room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name="address", nullable = false)
    private String address;

    @Column(name="total_desk", nullable = false)
    private int totalDesk;


    public Room(){}

    public Room(String name, String address, int totalDesk){
        this.name = name;
        this.address = address;
        this.totalDesk = totalDesk;
    }

    public Room(Long id, String name, String address, int totalDesk) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.totalDesk = totalDesk;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", totalDesk=" + totalDesk +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getTotalDesk() {
        return totalDesk;
    }

    public void setTotalDesk(int totalDesk) {
        this.totalDesk = totalDesk;
    }
}
