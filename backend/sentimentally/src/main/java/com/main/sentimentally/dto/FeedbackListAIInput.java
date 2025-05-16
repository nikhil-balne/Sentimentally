package com.main.sentimentally.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackListAIInput {

	private String categories;

	private int rating;

	private String feedback;

}
