package com.farmcollector.controller;

import com.farmcollector.dto.HarvestRequestDTO;
import com.farmcollector.dto.PlantingRequestDTO;
import com.farmcollector.repository.*;
import com.farmcollector.service.HarvestingService;
import com.farmcollector.service.PlantingService;
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


    private final FarmRepository farmRepository;
    private final CropRepository cropRepository;
    private final SeasonRepository seasonRepository;
    private final HarvestingService harvestingService;
    private final PlantingService plantingService;


    @GetMapping("/planting/form")
    public String showPlantingForm(Model model) {
        model.addAttribute("farms", farmRepository.findAll());
        model.addAttribute("crops", cropRepository.findAll());
        model.addAttribute("seasons", seasonRepository.findAll());
        return "planting-form";
    }

    @PostMapping("/planting/save")
    public String savePlanting(@ModelAttribute PlantingRequestDTO requestDTO, Model model) {
        Long plantingId = plantingService.savePlanting(requestDTO);
        model.addAttribute("plantingId", plantingId);
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
