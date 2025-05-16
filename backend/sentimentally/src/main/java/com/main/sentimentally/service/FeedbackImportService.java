package com.main.sentimentally.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.main.sentimentally.dto.BazaarVoiceResponse;
import com.main.sentimentally.dto.GoogleReviewsResponse;
import com.main.sentimentally.entity.InputFeedback;
import com.main.sentimentally.entity.Property;
import com.main.sentimentally.repository.InputFeedbackRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class FeedbackImportService {

	private InputFeedbackRepository inputFeedbackRepository;

	private ObjectMapper objectMapper;

	@Transactional
	public List<InputFeedback> importFeedbackDataFromFileUploads(MultipartFile bazaarVoiceFile,
			MultipartFile googleReviewsFile) throws Exception {
		// Delete existing data
		inputFeedbackRepository.deleteAll();

		List<InputFeedback> allFeedback = new ArrayList<>();

		if (bazaarVoiceFile != null && !bazaarVoiceFile.isEmpty()) {
			allFeedback.addAll(parseBazaarVoiceFile(bazaarVoiceFile));
		}

		if (googleReviewsFile != null && !googleReviewsFile.isEmpty()) {
			allFeedback.addAll(parseGoogleReviewsFile(googleReviewsFile));
		}

		return inputFeedbackRepository.saveAll(allFeedback);
	}

	@Transactional
	public List<InputFeedback> importFeedbackDataFromOnlineSources() throws Exception {
		// Delete existing data
		inputFeedbackRepository.deleteAll();

		List<InputFeedback> allFeedback = new ArrayList<>();
		allFeedback.addAll(loadFromBazaarVoice());
		allFeedback.addAll(loadFromGoogleReviews());

		return inputFeedbackRepository.saveAll(allFeedback);
	}

	// Refactored methods to convert responses into InputFeedback
	private List<InputFeedback> convertBazaarVoiceResponses(List<BazaarVoiceResponse> responses) {
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

	private List<InputFeedback> convertGoogleReviewsResponses(List<GoogleReviewsResponse> responses) {
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

	// Parsing methods
	private List<InputFeedback> parseBazaarVoiceFile(MultipartFile file) throws IOException {
		List<BazaarVoiceResponse> responses = objectMapper.readValue(file.getInputStream(), new TypeReference<>() {
		});
		return convertBazaarVoiceResponses(responses);
	}

	private List<InputFeedback> parseGoogleReviewsFile(MultipartFile file) throws IOException {
		List<GoogleReviewsResponse> responses = objectMapper.readValue(file.getInputStream(), new TypeReference<>() {
		});
		return convertGoogleReviewsResponses(responses);
	}

	// Loading from local JSON files
	private List<InputFeedback> loadFromBazaarVoice() throws Exception {
		InputStream input = new ClassPathResource("bazaar_voice_response.json").getInputStream();
		List<BazaarVoiceResponse> responses = objectMapper.readValue(input, new TypeReference<>() {
		});
		return convertBazaarVoiceResponses(responses);
	}

	private List<InputFeedback> loadFromGoogleReviews() throws Exception {
		InputStream input = new ClassPathResource("google_reviews_response.json").getInputStream();
		List<GoogleReviewsResponse> responses = objectMapper.readValue(input, new TypeReference<>() {
		});
		return convertGoogleReviewsResponses(responses);
	}

}
