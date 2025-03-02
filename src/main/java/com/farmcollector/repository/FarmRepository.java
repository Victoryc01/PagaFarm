package com.farmcollector.repository;

import com.farmcollector.entity.Farm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FarmRepository extends JpaRepository <Farm, Long> {
}
