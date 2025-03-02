package com.farmcollector.dto;


public record PlantingRequestDTO(
        Long farmId,
        Long cropId,
        Long seasonId,
        double areaInAcres,
        double expectedProduct

) {
}
