package com.main.sentimentally.controller;

import com.main.sentimentally.entity.Feedback;
import com.main.sentimentally.service.FeedbackService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    // TODO: Need to accept date input
    @GetMapping("/filter")
    public List<Feedback> getAnalysedFeedback(
            @RequestParam("categoryId") Integer categoryId,
            @RequestParam("propertyId") String propertyId) {

        return feedbackService.getFilteredFeedbacks(categoryId, propertyId);
    }
}
