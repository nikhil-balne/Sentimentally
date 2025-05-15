package com.main.sentimentally.controller;

import com.main.sentimentally.ai.IncidentAnalysisService;
import com.main.sentimentally.dto.IncidentAIResponse;
import com.main.sentimentally.dto.IncidentDTO;
import com.main.sentimentally.dto.ResolveIncidentDTO;
import com.main.sentimentally.entity.Incident;
import com.main.sentimentally.entity.Property;
import com.main.sentimentally.service.IncidentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/incidents")
public class IncidentController {

    private final IncidentService incidentService;

    private final IncidentAnalysisService incidentAnalysisService;

    @GetMapping("/")
    public List<Incident> getAllIncidents() {
        return incidentService.getAllIncidents();
    }

    @PostMapping("/")
    public  Incident saveIncident(@RequestBody IncidentDTO incidentDTO){
        Incident incident = new Incident();
        Property property = new Property();
        property.setId(incidentDTO.getPropertyId());
        incident.setIncidentText(incidentDTO.getIncidentText());
        incident.setProperty(property);
        incident.setIsResolved(false);
        incident.setCreatedTsz(OffsetDateTime.now());
        IncidentAIResponse incidentAIResponse = incidentAnalysisService.analyseData(incidentDTO.getIncidentText(), incidentDTO.getPropertyId());
        incident.setCategory(incidentAIResponse.getCategory());
        incident.setSeverity(incidentAIResponse.getSeverity());
        return incidentService.saveIncident(incident);
    }

    @PutMapping("/")
    public  Incident resolveIncident(@RequestBody ResolveIncidentDTO incidentDTO){
        return incidentService.resolveIncident(incidentDTO);
    }
}
