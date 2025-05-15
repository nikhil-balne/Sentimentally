package com.main.sentimentally.dto;

import com.main.sentimentally.entity.Category;
import com.main.sentimentally.entity.Severity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncidentAIResponse {
    private Category category;
    private Severity severity;
    private String summary;
}
