package com.farmcollector.repository;

import com.farmcollector.entity.Planting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlantingRepository extends JpaRepository<Planting, Long> {

}
