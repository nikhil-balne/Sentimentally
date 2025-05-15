package com.main.sentimentally.ai;

import com.main.sentimentally.dto.FeedbackAIResponse;
import com.main.sentimentally.entity.Category;
import com.main.sentimentally.record.FeedbackAIRecord;
import com.main.sentimentally.service.CategoryService;
import com.main.sentimentally.utils.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class FeedbackAnalysisService {

	private final OpenAiChatModel chatModel;

	private final CategoryService categoryService;

    public FeedbackAIResponse analyzeData(String feedbackText, int rating){
        String template =
                """
                    You are an intelligent feedback processing engine. Given an feedback report or description, analyze the content carefully and return a structured JSON response. Your task is to identify:
                    1. Categories – Choose the most relevant categories the feedback may fall into from the following list:{categories}
                    If none of these categories are suitable, generate a new relevant categories as a string as required.
                    2. Rating – Assess the feedback based on impact, tone and categories and generate a rating out of 5 then compare the generated rating with the user provided rating and generate a final appropriate rating.
                    3. Summary – Generate a concise and accurate summary of the feedback in 1–2 sentences, capturing the key issue.
                    Input Format:
                    You will receive an unstructured text describing an feedback.
                    Output Format:
                    Return the result in JSON format with the following structure:
                    {
                      "category": "[<category-names>]",
                      "rating": "0 to 5",
                      "summary": "<brief summary of the feedback>"
                    }
                    Example:
                    feedback Text: "The air conditioning had issues and made loud noises all night, and i couldn't sleep."
                    rating: 1
                    Expected JSON Output:
                    {
                      "category": "Maintenance",
                      "rating": "3.2",
                      "summary": "The air conditioning unit in room 402 is noisy, disturbing the guest's sleep."
                    }
                    Now, process the following user provided feedback and rating: feedback: {feedback}, rating : {rating}
                """;
        String categories =  StringUtils.joinWithComma(categoryService.getAllCategories().stream().map(Category::getName).toArray(String[]::new));
        FeedbackAIResponse feedbackAIResponse = new FeedbackAIResponse();
        var outputParser = new BeanOutputConverter<>(FeedbackAIRecord.class);
        String format = outputParser.getFormat();
        Message message = new SystemPromptTemplate(template).createMessage(
                Map.of("categories", categories, "feedback", feedbackText, "rating", rating, "format", format)
        );
        Prompt prompt = new Prompt(List.of(message));
        String generation = chatModel
                .call(prompt)
                .getResult()
                .getOutput().getText();
        assert generation != null;
        FeedbackAIRecord feedbackAIRecord = outputParser.convert(generation);
        assert feedbackAIRecord != null;
        feedbackAIResponse.setCategory(StringUtils.joinWithComma(feedbackAIRecord.categories()));
        feedbackAIResponse.setRating(feedbackAIRecord.rating());
        feedbackAIResponse.setSummary(feedbackAIRecord.summary());
        return feedbackAIResponse;
    }
}
