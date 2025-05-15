package com.main.sentimentally.controller;

import com.main.sentimentally.entity.Property;
import com.main.sentimentally.service.PropertyService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/properties")
public class PropertyController {

	private final PropertyService propertyService;

	@GetMapping("/")
	public List<Property> getAllProperties() {
		return propertyService.getAllProperties();
	}

}