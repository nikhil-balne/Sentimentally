package com.main.sentimentally.ai;

import com.main.sentimentally.dto.IncidentAIResponse;
import com.main.sentimentally.entity.Category;
import com.main.sentimentally.entity.Property;
import com.main.sentimentally.entity.Severity;
import com.main.sentimentally.record.IncidentAIRecord;
import com.main.sentimentally.service.CategoryService;
import com.main.sentimentally.service.PropertyService;
import com.main.sentimentally.service.SeverityService;
import com.main.sentimentally.utils.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class IncidentAnalysisService {

	private final ChatClient chatClient;

	private final CategoryService categoryService;

	private final SeverityService severityService;

	private final PropertyService propertyService;

	public IncidentAIResponse analyzeData(String incidentText, String propertyId) {
		Property property = propertyService.getProperty(propertyId);
		String template = """
				    You are an intelligent incident classification engine. Given an incident report or description, analyze the content carefully and return a structured JSON response. Your task is to identify:
				    1. Category – Choose the most relevant category from the following list:{categoriesinput}
				    If none of these categories are suitable, generate a new relevant category as a string.
				    2. Severity – Assess the severity of the incident based on urgency, impact, and tone of the incident using these levels:{levelsinput}.
				    3. Summary – Generate a concise and accurate summary of the incident in 3–4 sentences, capturing the key issue.
				    Input Format:
				    You will receive an unstructured text describing an incident.
				    Output Format:
				    Return the result in JSON format with the following fields: category, severity and summary
				    Now, process the following incident: {incidentinput}
				""";
		List<Category> categoryList = categoryService.getAllCategories();
		List<Severity> severityList = severityService.getAllSeverities();
		String categories = StringUtils
			.joinWithComma(categoryList.stream().map(Category::getName).toArray(String[]::new));
		String levels = StringUtils.joinWithComma(severityList.stream().map(Severity::getName).toArray(String[]::new));
		;
		IncidentAIResponse incidentAIResponse = new IncidentAIResponse();
		IncidentAIRecord incidentAIRecord = chatClient.prompt()
			.user(u -> u.text(template)
				.param("categoriesinput", categories)
				.param("levelsinput", levels)
				.param("incidentinput", property.getIncidentSummary() + incidentText))
			.call()
			.entity(IncidentAIRecord.class);
		assert incidentAIRecord != null;
		String[] categoryArray = new String[] { incidentAIRecord.category() };
		List<Category> newCategories = categoryService.getNewCategories(categoryArray, categoryList);
		if (!newCategories.isEmpty()) {
			List<Category> addedCategories = categoryService.saveCategories(newCategories);
			incidentAIResponse.setCategory(addedCategories.get(0));
		}
		else {
			List<Category> existingCategories = categoryService.findExistingCategories(categoryArray, categoryList);
			incidentAIResponse.setCategory(existingCategories.get(0));
		}
		incidentAIResponse
			.setSeverity(severityService.findExistingSeverity(incidentAIRecord.severity(), severityList).get(0));
		incidentAIResponse.setSummary(incidentAIRecord.summary());
		if (property.getIncidentSummary() == null || property.getIncidentSummary().isEmpty()) {
			property.setFeedbackSummary("");
		}
		property.setIncidentSummary(incidentAIRecord.summary());
		propertyService.saveProperty(property);
		return incidentAIResponse;
	}

}
