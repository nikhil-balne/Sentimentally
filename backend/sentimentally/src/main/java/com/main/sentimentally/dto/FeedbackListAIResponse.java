package com.main.sentimentally.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackListAIResponse {
    private String summary;
    private int rating;
    private String improvements;
    private String xCoordinate;
    private String yCoordinate;
    private String[] xPoints;
    private String[] yPoints;
}
