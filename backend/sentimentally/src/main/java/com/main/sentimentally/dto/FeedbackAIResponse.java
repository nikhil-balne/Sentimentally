package com.main.sentimentally.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackAIResponse {

	private String category;

	private int rating;

	private String summary;

}
