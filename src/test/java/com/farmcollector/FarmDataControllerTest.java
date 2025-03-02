package com.farmcollector;


import com.farmcollector.controller.FarmDataController;
import com.farmcollector.dto.HarvestRequestDTO;
import com.farmcollector.dto.PlantingRequestDTO;
import com.farmcollector.entity.Farm;
import com.farmcollector.entity.Crop;
import com.farmcollector.entity.Season;
import com.farmcollector.repository.*;
import com.farmcollector.service.HarvestingService;
import com.farmcollector.service.PlantingService;
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

    @Mock private FarmRepository farmRepository;
    @Mock private CropRepository cropRepository;
    @Mock private SeasonRepository seasonRepository;
    @Mock private ReportRepository reportRepository;
    @Mock private HarvestingService harvestingService;
    @Mock private PlantingService plantingService;
    @Mock private Model model;

    @InjectMocks private FarmDataController farmDataController;
    private PlantingRequestDTO plantingRequestDTO;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(farmDataController).build();
        plantingRequestDTO = new PlantingRequestDTO(1L, 2L, 3L, 5.0, 100.0);

    }

    @Test
    void testShowPlantingForm() throws Exception {
        List<Farm> farms = List.of(new Farm(1L, "PagaFarm"));
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
    void testSavePlanting() {
        // Arrange
        Long mockPlantingId = 10L;
        when(plantingService.savePlanting(plantingRequestDTO)).thenReturn(mockPlantingId);

        // Act
        String viewName = farmDataController.savePlanting(plantingRequestDTO, model);

        // Assert
        verify(plantingService, times(1)).savePlanting(plantingRequestDTO);
        verify(model, times(1)).addAttribute("plantingId", mockPlantingId);
        assertEquals("success", viewName);
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

        doAnswer(invocation -> null).when(harvestingService).saveHarvesting(any(HarvestRequestDTO.class), any(Long.class));


        String viewName = farmDataController.saveHarvesting(actualProduct, plantingId);

        assertEquals("success", viewName);
        verify(harvestingService, times(1)).saveHarvesting(any(HarvestRequestDTO.class), eq(plantingId));
    }


}
