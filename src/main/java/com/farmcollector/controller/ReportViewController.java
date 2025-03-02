package com.farmcollector.controller;

import com.farmcollector.entity.Report;
import com.farmcollector.repository.HarvestingRepository;
import com.farmcollector.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api")

/*
* This class exposes an API for viewing planting and harvesting reports
* */
public class ReportViewController {

    private final ReportRepository reportRepository;

    @GetMapping("/reports")
    public String showAllSeasonsReport(Model model) {
        List<Report> reportData = reportRepository.findAll();
        model.addAttribute("reportData", reportData);
        return "report";
    }

}
