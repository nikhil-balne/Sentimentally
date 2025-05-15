package com.main.sentimentally.service;

import com.main.sentimentally.entity.Feedback;
import com.main.sentimentally.repository.FeedbackRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class FeedbackService {

	private final FeedbackRepository feedbackRepository;

	public OffsetDateTime parseDateRange(String range) {
		OffsetDateTime now = OffsetDateTime.now();
		return switch (range.toUpperCase()) {
			case "1D" -> now.minusDays(1);
			case "1W" -> now.minusWeeks(1);
			case "1M" -> now.minusMonths(1);
			default -> throw new IllegalArgumentException("Invalid date range: " + range);
		};
	}

	public List<Feedback> getFilteredFeedbacks(Integer categoryId, String propertyId, String state, String brandId,
			String relativeDate) {
		OffsetDateTime startDate = parseDateRange(relativeDate);

		return feedbackRepository.findFeedbackByCategoryIdAndPropertyId(categoryId, propertyId, state, brandId,
				startDate);
	}

	public  Feedback saveFeedback(Feedback feedback){
		return  feedbackRepository.save(feedback);
	}

}
