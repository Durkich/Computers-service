package com.Kalabekov.Computersservice.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @ToString @Getter
@Entity
@Table(name = "software",schema = "infrastructure")
public class Software {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "software_name")
    private String softwareName;

    @Column(name = "software_version")
    private String softwareVersion;

    @Column(name = "is_licensed")
    private boolean isLicensed;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "computer_id")
    private Computer computer;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "laptop_id")
    private Laptop laptop;
    
}
