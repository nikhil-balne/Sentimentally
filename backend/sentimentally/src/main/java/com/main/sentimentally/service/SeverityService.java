package com.main.sentimentally.service;

import com.main.sentimentally.entity.Severity;
import com.main.sentimentally.repository.SeverityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SeverityService {

	private final SeverityRepository severityRepository;

	public List<Severity> getAllSeverities() {
		return severityRepository.findAll();
	}

	public List<Severity> findExistingSeverity(String severity, List<Severity> SeverityList) {
		return SeverityList.stream()
			.filter(sev -> Objects.equals(sev.getName(), severity))
			.collect(Collectors.toList());
	}

}
