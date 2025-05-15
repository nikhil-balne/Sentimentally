package com.main.sentimentally.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncidentDTO {

	private String incidentText;

	private String propertyId;

}
