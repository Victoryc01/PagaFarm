package com.farmcollector;


import com.farmcollector.controller.FarmDataController;
import com.farmcollector.dto.HarvestRequestDTO;
import com.farmcollector.entity.Farm;
import com.farmcollector.entity.Crop;
import com.farmcollector.entity.Season;
import com.farmcollector.entity.Planting;
import com.farmcollector.entity.Report;
import com.farmcollector.repository.*;
import com.farmcollector.service.HarvestingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
public class FarmDataControllerTest {

    private MockMvc mockMvc;

    @Mock private PlantingRepository plantingRepository;
    @Mock private FarmRepository farmRepository;
    @Mock private CropRepository cropRepository;
    @Mock private SeasonRepository seasonRepository;
    @Mock private ReportRepository reportRepository;
    @Mock private HarvestingService harvestingService;
    @Mock private Model model;

    @InjectMocks private FarmDataController farmDataController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(farmDataController).build();
    }

    @Test
    void testShowPlantingForm() throws Exception {
        List<Farm> farms = List.of(new Farm(1L, "Farm A"));
        List<Crop> crops = List.of(new Crop(1L, "Maize"));
        List<Season> seasons = List.of(new Season(1L, "Summer"));

        when(farmRepository.findAll()).thenReturn(farms);
        when(cropRepository.findAll()).thenReturn(crops);
        when(seasonRepository.findAll()).thenReturn(seasons);

        String viewName = farmDataController.showPlantingForm(model);

        assertEquals("planting-form", viewName);
        verify(model).addAttribute("farms", farms);
        verify(model).addAttribute("crops", crops);
        verify(model).addAttribute("seasons", seasons);
    }

    @Test
    void testSavePlanting() throws Exception {
        Long farmId = 1L, cropId = 2L, seasonId = 3L;
        double area = 10.5, expectedProduct = 200.0;
        Planting planting = new Planting();
        planting.setId(1L);

        when(farmRepository.findById(farmId)).thenReturn(Optional.of(new Farm(farmId, "Farm A")));
        when(cropRepository.findById(cropId)).thenReturn(Optional.of(new Crop(cropId, "Maize")));
        when(seasonRepository.findById(seasonId)).thenReturn(Optional.of(new Season(seasonId, "Summer")));
        when(plantingRepository.save(any(Planting.class))).thenReturn(planting);

        String viewName = farmDataController.savePlanting(farmId, cropId, seasonId, area, expectedProduct, model);

        assertEquals("success", viewName);
        verify(model).addAttribute("plantingId", planting.getId());
        verify(reportRepository, times(1)).save(any(Report.class));
    }

    @Test
    void testShowHarvestingForm() throws Exception {
        mockMvc.perform(get("/api/harvesting/form"))
                .andExpect(status().isOk())
                .andExpect(view().name("harvesting-form"));
    }

    @Test
    void testSaveHarvesting() throws Exception {
        Long plantingId = 1L;
        double actualProduct = 250.0;
        HarvestRequestDTO requestDTO = new HarvestRequestDTO(actualProduct);

        doAnswer(invocation -> null).when(harvestingService).saveHarvesting(any(HarvestRequestDTO.class), any(Long.class));


        String viewName = farmDataController.saveHarvesting(actualProduct, plantingId);

        assertEquals("success", viewName);
        verify(harvestingService, times(1)).saveHarvesting(any(HarvestRequestDTO.class), eq(plantingId));
    }


}
