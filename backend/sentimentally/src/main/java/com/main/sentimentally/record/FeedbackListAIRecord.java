package com.main.sentimentally.record;

public record FeedbackListAIRecord(    String summary,
                                       int rating,
                                       String improvements,
                                       String xCoordinate,
                                       String yCoordinate,
                                       String[] xPoints,
                                       String[] yPoints) {
}
