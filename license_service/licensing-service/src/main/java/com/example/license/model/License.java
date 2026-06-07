package com.example.license.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;


@Getter
@Setter
@ToString(exclude = "idType")
@Entity
@Table(name = "licenses")
public class License {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "namepo")
    private String namePo;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "idtype")
    private Type idType;

    @Column(name = "startdate")
    private LocalDate startDate;

    @Column(name = "enddate")
    private LocalDate endDate;

    @Column(name = "countstart")
    private Integer countStart;

    @Column(name = "countnow")
    private Integer countNow;
}
