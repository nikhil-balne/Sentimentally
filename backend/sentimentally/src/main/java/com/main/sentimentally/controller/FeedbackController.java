package com.main.sentimentally.controller;

import com.main.sentimentally.entity.Feedback;
import com.main.sentimentally.service.FeedbackService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/feedbacks")
@AllArgsConstructor
@RequestMapping("/feedbacks")
public class FeedbackController {

    private final FeedbackService feedbackService;

    // TODO: Need to accept date input
    @GetMapping("/")
    public List<Feedback> getAnalysedFeedback(
            @RequestParam(value = "categoryId", required = false) Integer categoryId,
            @RequestParam(value = "propertyId", required = false) String propertyId,
            @RequestParam(value = "state", required = false) String state,
            @RequestParam(value = "brand", required = false) String brandId,
            @RequestParam(value = "date", defaultValue = "1W") String relativeDate) {

        return feedbackService.getFilteredFeedbacks(categoryId, propertyId, state, brandId, relativeDate);
    }
}
