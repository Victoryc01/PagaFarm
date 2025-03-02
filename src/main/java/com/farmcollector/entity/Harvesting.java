package com.farmcollector.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Harvesting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double actualProduct;

    @OneToOne
    @JoinColumn(name = "planting_id", nullable = false)
    private Planting planting; //Each harvesting is linked to a single planting record


}
