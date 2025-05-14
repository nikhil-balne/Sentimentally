package com.main.sentimentally.service;

import com.main.sentimentally.entity.Incident;
import com.main.sentimentally.entity.Property;
import com.main.sentimentally.repository.IncidentRepository;
import com.main.sentimentally.repository.PropertyRepository;
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
}
