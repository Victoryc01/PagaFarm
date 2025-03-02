package com.farmcollector.service.impl;

import com.farmcollector.dto.PlantingRequestDTO;
import com.farmcollector.entity.Planting;
import com.farmcollector.entity.Report;
import com.farmcollector.repository.*;
import com.farmcollector.service.PlantingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlantingServiceImpl implements PlantingService {

    private final FarmRepository farmRepository;
    private final CropRepository cropRepository;
    private final SeasonRepository seasonRepository;
    private final PlantingRepository plantingRepository;
    private final ReportRepository reportRepository;


    @Override
    public Long savePlanting(PlantingRequestDTO requestDTO) {
        var farm = farmRepository.findById(requestDTO.farmId()).orElseThrow();
        var crop = cropRepository.findById(requestDTO.cropId()).orElseThrow();
        var season = seasonRepository.findById(requestDTO.seasonId()).orElseThrow();

        var planting = new Planting();
        planting.setFarm(farm);
        planting.setCrop(crop);
        planting.setSeason(season);
        planting.setAreaInAcres(requestDTO.areaInAcres());
        planting.setExpectedProduct(requestDTO.expectedProduct());

        Long plantingId = plantingRepository.save(planting).getId();

        var report = new Report();
        report.setSeason(season.getName());
        report.setFarm(farm.getName());
        report.setCrop(crop.getName());
        report.setExpectedProduct(requestDTO.expectedProduct());
        report.setPlanting(planting);
        reportRepository.save(report);

        return plantingId;
    }
}
