package com.farmcollector.controller;

import com.farmcollector.dto.HarvestRequestDTO;
import com.farmcollector.entity.Planting;
import com.farmcollector.entity.Report;
import com.farmcollector.repository.*;
import com.farmcollector.service.HarvestingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@Controller
@RequestMapping("/api")

/*
* This class handles form submissions for planting and harvesting,
* Saves planting details and creates a Report entry,
* Saves harvesting data and updates the report
* */
public class FarmDataController {


    private final PlantingRepository plantingRepository;
    private final FarmRepository farmRepository;
    private final CropRepository cropRepository;
    private final SeasonRepository seasonRepository;
    private final ReportRepository reportRepository;
    private final HarvestingService harvestingService;


    @GetMapping("/planting/form")
    public String showPlantingForm(Model model) {
        model.addAttribute("farms", farmRepository.findAll());
        model.addAttribute("crops", cropRepository.findAll());
        model.addAttribute("seasons", seasonRepository.findAll());
        return "planting-form";
    }

    @PostMapping("/planting/save")
    public String savePlanting(@RequestParam Long farmId, Long cropId, Long seasonId, double areaInAcres, double expectedProduct, Model model) {
        Planting planting = new Planting();
        planting.setFarm(farmRepository.findById(farmId).orElseThrow());
        planting.setCrop(cropRepository.findById(cropId).orElseThrow());
        planting.setSeason(seasonRepository.findById(seasonId).orElseThrow());
        planting.setAreaInAcres(areaInAcres);
        planting.setExpectedProduct(expectedProduct);

        Long plantingId = plantingRepository.save(planting).getId();

        model.addAttribute("plantingId", plantingId);


        var report = new Report();
        report.setSeason(seasonRepository.findById(seasonId).get().getName());
        report.setFarm(farmRepository.findById(farmId).get().getName());
        report.setCrop(cropRepository.findById(cropId).get().getName());
        report.setExpectedProduct(expectedProduct);
        report.setPlanting(planting);
        reportRepository.save(report);

        return "success";
    }


    @GetMapping("/harvesting/form")
    public String showHarvestingForm() {
        return "harvesting-form";
    }


    @PostMapping("/harvesting/save")
    public String saveHarvesting(@RequestParam double actualProduct, Long plantingId ) {
        var requestDTO = new HarvestRequestDTO(actualProduct);
        harvestingService.saveHarvesting(requestDTO, plantingId);
        return "success";
    }

}
