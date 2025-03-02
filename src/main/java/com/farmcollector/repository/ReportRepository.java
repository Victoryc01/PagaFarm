package com.farmcollector.repository;

import com.farmcollector.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface ReportRepository extends JpaRepository<Report, Long> {

   //query to fetch planting id
   @Query("SELECT r FROM Report r WHERE r.planting.id = :plantingId")
   Optional<Report> findByPlantingId(@Param("plantingId") Long plantingId);
}
