package com.main.sentimentally.service;

import com.main.sentimentally.entity.Severity;
import com.main.sentimentally.repository.SeverityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SeverityService {

    private  final SeverityRepository severityRepository;

    public List<Severity> getAllSeverities(){return severityRepository.findAll();}
}
