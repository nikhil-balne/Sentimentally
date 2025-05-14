package com.main.sentimentally.controller;

import com.main.sentimentally.entity.Incident;
import com.main.sentimentally.service.IncidentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class IncidentController {

    private final IncidentService incidentService;

    @GetMapping("/incidents")
    public List<Incident> getAllIncidents() {
        return incidentService.getAllIncidents();
    }
}
