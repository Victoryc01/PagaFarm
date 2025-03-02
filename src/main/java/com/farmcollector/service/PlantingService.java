package com.farmcollector.service;

import com.farmcollector.dto.PlantingRequestDTO;

public interface PlantingService {
    Long savePlanting(PlantingRequestDTO requestDTO);
}
