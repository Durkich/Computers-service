package com.Kalabekov.Computersservice.model;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Setter
@ToString
@Getter
@Entity
@Table(name = "computers",schema = "infrastructure")
public class Computer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "inventory_number")
    private String inventoryNumber;

    @Column(name = "graphic_adapter")
    private String graphicAdapter;

    @Column(name = "processor")
    private String processor;

    @Column(name = "rom")
    private Integer rom;

    @Column(name = "ram")
    private Integer ram;

    @Column(name = "room_number")
    private String roomNumber;

    @JsonManagedReference
    @OneToMany(mappedBy = "computer", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Peripheral> peripherals = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "computer", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Software> software = new ArrayList<>();

}