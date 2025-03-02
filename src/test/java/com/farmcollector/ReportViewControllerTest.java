package com.farmcollector;


import com.farmcollector.controller.ReportViewController;
import com.farmcollector.entity.Report;
import com.farmcollector.repository.ReportRepository;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
public class ReportViewControllerTest {


        private MockMvc mockMvc;

        @Mock private ReportRepository reportRepository;
        @Mock private Model model;

        @InjectMocks private ReportViewController reportViewController;

        @BeforeEach
        void setup() {
            mockMvc = MockMvcBuilders.standaloneSetup(reportViewController).build();
        }

        @Test
        void testShowAllSeasonsReport() throws Exception {
            List<Report> reports = List.of(new Report(), new Report());

            when(reportRepository.findAll()).thenReturn(reports);

            String viewName = reportViewController.showAllSeasonsReport(model);

            assertEquals("report", viewName);
            verify(model).addAttribute("reportData", reports);
        }

        @Test
        void testGetReportsAPI() throws Exception {
            mockMvc.perform(get("/api/reports"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("report"));
        }


}
