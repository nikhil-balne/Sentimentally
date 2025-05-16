package com.main.sentimentally.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.main.sentimentally.dto.BazaarVoiceResponse;
import com.main.sentimentally.dto.GoogleReviewsResponse;
import com.main.sentimentally.entity.InputFeedback;
import com.main.sentimentally.entity.Property;
import com.main.sentimentally.repository.InputFeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackImportService {

    private final InputFeedbackRepository inputFeedbackRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public List<InputFeedback> importFeedbackDataFromOnlineSources() throws Exception {
        // Delete existing data
        inputFeedbackRepository.deleteAll();
        List<InputFeedback> allFeedback = new ArrayList<>();
        allFeedback.addAll(loadFromBazaarVoice());
        allFeedback.addAll(loadFromGoogleReviews());
        // add new json data
        return inputFeedbackRepository.saveAll(allFeedback);
    }

    private List<InputFeedback> loadFromBazaarVoice() throws Exception {
        InputStream input = new ClassPathResource("bazaar_voice_responses.json").getInputStream();
        List<BazaarVoiceResponse> responses = objectMapper.readValue(input, new TypeReference<>() {});
        List<InputFeedback> feedbackList = new ArrayList<>();

        for (BazaarVoiceResponse r : responses) {
            InputFeedback feedback = new InputFeedback();
            feedback.setFeedbackText(r.getReviewData());
            feedback.setRating(r.getRating());
            feedback.setCreatedTsz(r.getDate());
            Property property = new Property();
            property.setId(r.getPropertyId());
            feedback.setProperty(property);
            feedbackList.add(feedback);
        }

        return feedbackList;
    }

    private List<InputFeedback> loadFromGoogleReviews() throws Exception {
        InputStream input = new ClassPathResource("google_reviews_responses.json").getInputStream();
        List<GoogleReviewsResponse> responses = objectMapper.readValue(input, new TypeReference<>() {});
        List<InputFeedback> feedbackList = new ArrayList<>();

        for (GoogleReviewsResponse r : responses) {
            InputFeedback feedback = new InputFeedback();
            feedback.setFeedbackText(r.getReview());
            feedback.setRating(r.getRating());
            feedback.setCreatedTsz(r.getDate());
            Property property = new Property();
            property.setId(r.getPropertyId());
            feedback.setProperty(property);
            feedbackList.add(feedback);
        }

        return feedbackList;
    }
}
