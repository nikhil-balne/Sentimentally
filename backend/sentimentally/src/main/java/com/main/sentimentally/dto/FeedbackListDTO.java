package com.main.sentimentally.dto;

import com.main.sentimentally.entity.Feedback;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackListDTO {

	List<Feedback> feedbackList;

	FeedbackListAIResponse feedbackListAIResponse;

}
