package com.Kalabekov.Computersservice.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @ToString @Getter
@Entity
@Table(name = "peripherals",schema = "infrastructure")
public class Peripheral {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "peripheral_name")
    private String peripheralName;

    @Column(name = "peripheral_type")
    private String peripheralType;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "computer_id")
    private Computer computer;
    
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "laptop_id")
    private Laptop laptop;
}
