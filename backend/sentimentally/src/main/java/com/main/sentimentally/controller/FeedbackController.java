package com.main.sentimentally.controller;

import com.main.sentimentally.ai.FeedbackAnalysisService;
import com.main.sentimentally.dto.FeedbackAIResponse;
import com.main.sentimentally.dto.FeedbackDTO;
import com.main.sentimentally.dto.FeedbackListDTO;
import com.main.sentimentally.entity.Feedback;
import com.main.sentimentally.entity.InputFeedback;
import com.main.sentimentally.entity.Property;
import com.main.sentimentally.service.FeedbackImportService;
import com.main.sentimentally.service.FeedbackService;
import com.main.sentimentally.service.PropertyService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/feedbacks")
@AllArgsConstructor
public class FeedbackController {

	private final FeedbackService feedbackService;

	private final FeedbackAnalysisService feedbackAnalysisService;

	private final FeedbackImportService feedbackImportService;

	@GetMapping("/")
	public FeedbackListDTO getAnalysedFeedback(@RequestParam(value = "categoryId", required = false) Integer categoryId,
			@RequestParam(value = "propertyId", required = false) String propertyId,
			@RequestParam(value = "state", required = false) String state,
			@RequestParam(value = "brand", required = false) String brandId,
			@RequestParam(value = "date", defaultValue = "1W") String relativeDate) {
		FeedbackListDTO feedbackListDTO = new FeedbackListDTO();
		List<Feedback> feedbackList = feedbackService.getFilteredFeedbacks(categoryId, propertyId, state, brandId,
				relativeDate);
		feedbackListDTO.setFeedbackList(feedbackList);
		if (feedbackListDTO.getFeedbackList().size() != 0) {
			feedbackListDTO.setFeedbackListAIResponse(feedbackAnalysisService.analyzeGroupedData(feedbackList));
		}
		return feedbackListDTO;
	}

	@PostMapping("/")
	public Feedback saveFeedBack(@RequestBody FeedbackDTO feedbackDTO) {
		Feedback feedback = new Feedback();
		Property property = new Property();
		property.setId(feedbackDTO.getPropertyId());
		feedback.setFeedbackText(feedbackDTO.getFeedbackText());
		feedback.setProperty(property);
		feedback.setCreatedTsz(OffsetDateTime.now());
		FeedbackAIResponse feedbackAIResponse = feedbackAnalysisService.analyzeData(feedbackDTO.getFeedbackText(),
				feedbackDTO.getRating(), feedbackDTO.getPropertyId());
		feedback.setCategoryIds(feedbackAIResponse.getCategory());
		feedback.setRating(feedbackAIResponse.getRating());
		return feedbackService.saveFeedback(feedback);
	}

	@PostMapping("/analyze")
	public String analyzeFeedbackData() {
		try {
			feedbackAnalysisService.analyzeOnlineScrapedData();
			return "Feedback data has been processed and saved.";
		}
		catch (Exception e) {
			return "An error occurred while processing feedback data";
		}
	}

	@PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String uploadAndProcessFeedback(
			@RequestParam(value = "bazaarVoiceFile", required = false) MultipartFile bazaarVoiceFile,
			@RequestParam(value = "googleReviewsFile", required = false) MultipartFile googleReviewsFile) {

		try {
			// Clear existing input data
			feedbackImportService.importFeedbackDataFromFileUploads(bazaarVoiceFile, googleReviewsFile);
			// feedbackAnalysisService.analyzeInputData();
			return "Feedback imported and processed successfully.";

		}
		catch (Exception e) {
			e.printStackTrace();
			return "Failed to import or process feedback: " + e.getMessage();
		}
	}

}
