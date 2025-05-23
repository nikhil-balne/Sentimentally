package com.main.sentimentally.controller;

import com.main.sentimentally.entity.Severity;
import com.main.sentimentally.service.SeverityService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestController
@RequestMapping("/severities")
@AllArgsConstructor
public class SeverityController {

	private final SeverityService severityService;

	@GetMapping("/")
	public List<Severity> getAllSeverities() {
		return severityService.getAllSeverities();
	}

}
