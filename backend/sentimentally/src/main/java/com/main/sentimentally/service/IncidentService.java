package com.main.sentimentally.service;

import com.main.sentimentally.dto.IncidentDTO;
import com.main.sentimentally.dto.ResolveIncidentDTO;
import com.main.sentimentally.entity.Incident;
import com.main.sentimentally.repository.IncidentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class IncidentService {

    private final IncidentRepository incidentRepository;

    public List<Incident> getAllIncidents() {
        return incidentRepository.findAll();
    }

    public Incident saveIncident(Incident newIncident){
        return  incidentRepository.save(newIncident);
    }

    public Incident resolveIncident(ResolveIncidentDTO incidentDTO) {
        Incident incident = incidentRepository.findByIdAndPropertyId(incidentDTO.getIncidentId(), incidentDTO.getPropertyId())
                .orElseThrow(() -> new IllegalArgumentException("Incident not found"));

        incident.setIsResolved(true);
        return incidentRepository.save(incident);
    }
}
