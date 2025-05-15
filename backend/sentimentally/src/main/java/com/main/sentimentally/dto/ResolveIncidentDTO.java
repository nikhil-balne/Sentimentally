package com.main.sentimentally.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResolveIncidentDTO {

	private Long incidentId;

	private String propertyId;

}
