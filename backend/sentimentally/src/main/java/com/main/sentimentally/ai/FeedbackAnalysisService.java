package com.main.sentimentally.ai;

import com.main.sentimentally.dto.FeedbackAIResponse;
import com.main.sentimentally.dto.FeedbackListAIInput;
import com.main.sentimentally.dto.FeedbackListAIResponse;
import com.main.sentimentally.entity.Category;
import com.main.sentimentally.entity.Feedback;
import com.main.sentimentally.entity.InputFeedback;
import com.main.sentimentally.entity.Property;
import com.main.sentimentally.record.FeedbackAIRecord;
import com.main.sentimentally.record.FeedbackListAIRecord;
import com.main.sentimentally.service.CategoryService;
import com.main.sentimentally.service.FeedbackImportService;
import com.main.sentimentally.service.FeedbackService;
import com.main.sentimentally.service.PropertyService;
import com.main.sentimentally.utils.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Service
@AllArgsConstructor
public class FeedbackAnalysisService {

	private final ChatClient chatClient;

	private final CategoryService categoryService;

	private final PropertyService propertyService;

	private final FeedbackImportService feedbackImportService;

	private final FeedbackService feedbackService;

	public FeedbackAIResponse analyzeData(String feedbackText, int rating, String propertyId) {
		Property property = propertyService.getProperty(propertyId);
		String template = """
				    You are an intelligent feedback processing engine. Given an feedback report or description, analyze the content carefully and return a structured JSON response. Your task is to identify:
				    1. Categories – Choose the most relevant categories the feedback may fall into from the following list:{categoriesinput}
				    If none of these categories are suitable, generate a new relevant categories as a string as required.
				    2. Rating – Assess the feedback based on impact, tone and categories and generate a rating out of 5 then compare the generated rating with the user provided rating and generate a final appropriate rating.
				    3. Summary – Generate a concise and accurate summary of the feedback in 1–2 sentences, capturing the key issue.
				    4. propertyRating - Generate a final rating for the property based on the current property rating given and the rating generated for the current feedback give more weightage to current property rating
				    5. propertySummary - Generate a final summary for the property based on the current property summary given and the summary generated for the current feedback.
				    Input Format:
				    You will receive an unstructured text describing an feedback.
				    Output Format:
				    Return the result in JSON format with the following structure: category, rating and summary
				    Now, process the following user provided feedback and rating: feedback: {feedbackinput}, rating : {ratinginput} current_property_summary: {current_property_summary_input} current_property_rating: {current_property_rating_input}
				""";
		List<Category> categoryList = categoryService.getAllCategories();
		String categories = StringUtils
			.joinWithComma(categoryList.stream().map(Category::getName).toArray(String[]::new));
		FeedbackAIResponse feedbackAIResponse = new FeedbackAIResponse();
		FeedbackAIRecord feedbackAIRecord = chatClient.prompt()
			.user(u -> u.text(template)
				.param("categoriesinput", categories)
				.param("feedbackinput", feedbackText)
				.param("ratinginput", rating)
				.param("current_property_rating_input", property.getFeedbackRating())
				.param("current_property_summary_input", property.getFeedbackSummary()))
			.call()
			.entity(FeedbackAIRecord.class);
		assert feedbackAIRecord != null;
		List<Category> newCategories = categoryService.getNewCategories(feedbackAIRecord.categories(), categoryList);
		List<String> categoryIds = new ArrayList<>();
		if (!newCategories.isEmpty()) {
			List<Category> addedCategories = categoryService.saveCategories(newCategories);
			addedCategories.forEach((category -> categoryIds.add(category.getId() + "")));
		}
		List<Category> existingCategories = categoryService.findExistingCategories(feedbackAIRecord.categories(),
				categoryList);
		if (!existingCategories.isEmpty()) {
			existingCategories.forEach((category -> categoryIds.add(category.getId() + "")));
		}
		feedbackAIResponse.setCategory(StringUtils.joinWithComma(categoryIds.toArray(new String[0])));
		feedbackAIResponse.setRating(feedbackAIRecord.rating());
		feedbackAIResponse.setSummary(feedbackAIRecord.summary());
		if (property.getIncidentSummary() == null || property.getIncidentSummary().isEmpty()) {
			property.setIncidentSummary("");
		}
		property.setFeedbackSummary(feedbackAIRecord.propertySummary());
		property.setFeedbackRating(feedbackAIRecord.propertyRating());
		propertyService.saveProperty(property);
		return feedbackAIResponse;
	}

	public static String buildListAnalyzePrompt(List<FeedbackListAIInput> feedbackList) {
		StringBuilder promptBuilder = new StringBuilder();
		promptBuilder.append("You are an AI assistant tasked with analyzing customer feedback data. ");
		promptBuilder.append("Each feedback entry includes categories, a rating (1 to 5), and a textual comment. ");
		promptBuilder.append(
				"Choose the categories only from the from the below supplied categories list do not create new categories");
		promptBuilder.append(
				"Analyze the following feedback entries and produce a JSON object with the following structure:\n\n");
		promptBuilder.append("{\n");
		promptBuilder.append("  \"summary\": \"A concise summary of the overall feedback.\",\n");
		promptBuilder.append("  \"rating\": Overall average rating as an integer,\n");
		promptBuilder.append("  \"improvements\": \"Key areas identified for improvement.\",\n");
		promptBuilder.append("  \"xCoordinate\": \"Label for the x-axis (e.g., 'Categories')\",\n");
		promptBuilder.append("  \"yCoordinate\": \"Label for the y-axis (e.g., 'Average Rating')\",\n");
		promptBuilder.append("  \"xPoints\": [\"Category1\", \"Category2\", ...],\n");
		promptBuilder.append("  \"yPoints\": [\"Rating for Category1\", \"Rating for Category2\", ...]\n");
		promptBuilder.append("}\n\n");
		promptBuilder.append("Feedback Entries:\n");

		int count = 1;
		for (FeedbackListAIInput feedback : feedbackList) {
			promptBuilder.append(count++)
				.append(". Categories: ")
				.append(feedback.getCategories())
				.append("; Rating: ")
				.append(feedback.getRating())
				.append("; Feedback: \"")
				.append(feedback.getFeedback())
				.append("\"\n");
		}

		promptBuilder.append("\nPlease analyze the above feedback and provide the JSON object as specified.");

		return promptBuilder.toString();
	}

	public FeedbackListAIResponse analyzeGroupedData(List<Feedback> feedbackList) {
		List<Category> categoryList = categoryService.getAllCategories();
		FeedbackListAIResponse feedbackListAIResponse = new FeedbackListAIResponse();
		List<FeedbackListAIInput> feedbackListAIInputList = new ArrayList<FeedbackListAIInput>();
		for (Feedback feedback : feedbackList) {
			FeedbackListAIInput feedbackListAIInput = new FeedbackListAIInput();
			feedbackListAIInput
				.setCategories(categoryService.getCategoryNamesString(feedback.getCategoryIds(), categoryList));
			feedbackListAIInput.setRating(feedback.getRating());
			feedbackListAIInput.setFeedback(feedback.getFeedbackText());
			feedbackListAIInputList.add(feedbackListAIInput);
		}
		String prompt = buildListAnalyzePrompt(feedbackListAIInputList);
		FeedbackListAIRecord feedbackListAIRecord = chatClient.prompt()
			.user(prompt)
			.call()
			.entity(FeedbackListAIRecord.class);
		assert feedbackListAIRecord != null;
		List<Category> newCategories = categoryService.getNewCategories(feedbackListAIRecord.xPoints(), categoryList);
		if (!newCategories.isEmpty()) {
			categoryService.saveCategories(newCategories);
		}
		feedbackListAIResponse.setImprovements(feedbackListAIRecord.improvements());
		feedbackListAIResponse.setSummary(feedbackListAIRecord.summary());
		feedbackListAIResponse.setRating(feedbackListAIRecord.rating());
		feedbackListAIResponse.setXCoordinate(feedbackListAIRecord.xCoordinate());
		feedbackListAIResponse.setYCoordinate(feedbackListAIRecord.yCoordinate());
		feedbackListAIResponse.setXPoints(feedbackListAIRecord.xPoints());
		feedbackListAIResponse.setYPoints(feedbackListAIRecord.yPoints());
		return feedbackListAIResponse;
	}

	public void analyzeOnlineScrapedData() throws Exception {
		// Import feedback data from online sources
		List<InputFeedback> feedbackInputList = feedbackImportService.importFeedbackDataFromOnlineSources();

		// Create an ExecutorService to manage threads
		analyzeInputData(feedbackInputList);

	}

	public void analyzeInputData(List<InputFeedback> feedbackInputList) throws Exception {
		ExecutorService executorService = Executors.newFixedThreadPool(15);

		List<Future<Feedback>> futureFeedbacks = new ArrayList<>();

		// Submit each feedback processing task to the executor
		for (InputFeedback feedback : feedbackInputList) {
			futureFeedbacks.add(executorService.submit(() -> {
				// Analyze the data in a separate thread
				FeedbackAIResponse analyzedFeedback = analyzeData(feedback.getFeedbackText(), feedback.getRating(),
						feedback.getProperty().getId());

				Feedback feedbackToSave = new Feedback();
				feedbackToSave.setFeedbackText(feedback.getFeedbackText());
				feedbackToSave.setCategoryIds(analyzedFeedback.getCategory());
				feedbackToSave.setRating(analyzedFeedback.getRating());
				feedbackToSave.setProperty(feedback.getProperty());
				feedbackToSave.setCreatedTsz(OffsetDateTime.now());

				return feedbackToSave;
			}));
		}
		List<Feedback> feedbacksToSave = new ArrayList<>();
		for (Future<Feedback> future : futureFeedbacks) {
			try {
				feedbacksToSave.add(future.get());
			}
			catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		feedbackService.saveFeedbacks(feedbacksToSave);

		executorService.shutdown();
	}

}
