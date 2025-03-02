package com.farmcollector.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor

public class Planting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "farm_id", nullable = false)
    private Farm farm; //Many plantings can be in a single farm

    @ManyToOne
    @JoinColumn(name = "crop_id", nullable = false)
    private Crop crop;

    @ManyToOne
    @JoinColumn(name = "season_id", nullable = false)
    private Season season;

    private double areaInAcres;
    private double expectedProduct;

    @OneToOne(mappedBy = "planting")
    private Harvesting harvesting; //One planting has one harvesting record

    @OneToOne(mappedBy = "planting")
    private Report report; //One planting generates one report


}
