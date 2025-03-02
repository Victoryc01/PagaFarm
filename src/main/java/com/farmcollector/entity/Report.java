package com.farmcollector.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor

/*
* This entity is a summary table that includes expected vs. actual product values.
* */

public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String season;
    private String farm;
    private String crop;
    private double expectedProduct;
    private double actualProduct;

    @OneToOne
    @JoinColumn(name = "planting_id", nullable = false)
    private Planting planting;  //Each report is linked to a planting record

}
