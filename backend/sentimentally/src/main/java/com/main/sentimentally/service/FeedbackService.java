package com.main.sentimentally.service;

import com.main.sentimentally.entity.Feedback;
import com.main.sentimentally.repository.FeedbackRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {
    private FeedbackRepository feedbackRepository;

    public List<Feedback> getFilteredFeedbacks(Integer categoryId, String propertyId) {
        return feedbackRepository.findFeedbackByCategoryIdAndPropertyId(categoryId, propertyId);
    }
}
