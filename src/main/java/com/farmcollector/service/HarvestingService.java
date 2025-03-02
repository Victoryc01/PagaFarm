package com.farmcollector.service;

import com.farmcollector.dto.CropResponseDTO;
import com.farmcollector.dto.FarmResponseDTO;
import com.farmcollector.dto.HarvestRequestDTO;
import com.farmcollector.dto.SeasonResponseDTO;
import com.farmcollector.entity.Harvesting;

import java.util.List;

public interface HarvestingService {

    Harvesting saveHarvesting(HarvestRequestDTO requestDTO, Long plantingId);
}
