package com.farmcollector.service.impl;

import com.farmcollector.dto.CropResponseDTO;
import com.farmcollector.dto.FarmResponseDTO;
import com.farmcollector.dto.HarvestRequestDTO;
import com.farmcollector.dto.SeasonResponseDTO;
import com.farmcollector.entity.Farm;
import com.farmcollector.entity.Harvesting;
import com.farmcollector.entity.Report;
import com.farmcollector.repository.*;
import com.farmcollector.service.HarvestingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

/*
* This class handles business logic for managing harvesting data,
* Saves harvesting data and updates the corresponding report
* */
public class HarvestingServiceImpl implements HarvestingService {

    private final HarvestingRepository harvestingRepository;
    private final PlantingRepository plantingRepository;
    private final ReportRepository reportRepository;


    //Saves the farmers harvesting data
    public Harvesting saveHarvesting(HarvestRequestDTO requestDTO, Long plantingId) {
        Harvesting harvesting = new Harvesting();
        harvesting.setActualProduct(requestDTO.actualProduct());
        harvesting.setPlanting(plantingRepository.findById(plantingId).get());

        harvestingRepository.save(harvesting);

        //updates the corresponding report
        Report report = reportRepository.findByPlantingId(plantingId).get();
        report.setActualProduct(requestDTO.actualProduct());
        reportRepository.save(report);

        return harvestingRepository.save(harvesting);
    }
}
