package com.main.sentimentally.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncidentAIResponse {
    private String category;
    private String severity;
    private String summary;
}
