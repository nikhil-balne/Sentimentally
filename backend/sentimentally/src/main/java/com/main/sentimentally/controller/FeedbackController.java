package com.main.sentimentally.controller;

import com.main.sentimentally.ai.FeedbackAnalysisService;
import com.main.sentimentally.dto.FeedbackAIResponse;
import com.main.sentimentally.dto.FeedbackDTO;
import com.main.sentimentally.dto.FeedbackListDTO;
import com.main.sentimentally.entity.Feedback;
import com.main.sentimentally.entity.InputFeedback;
import com.main.sentimentally.entity.Property;
import com.main.sentimentally.service.FeedbackService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/feedbacks")
@AllArgsConstructor
public class FeedbackController {

	private final FeedbackService feedbackService;

	private final FeedbackAnalysisService feedbackAnalysisService;

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
		feedbackListDTO.setFeedbackListAIResponse(feedbackAnalysisService.analyzeGroupedData(feedbackList));
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

}
