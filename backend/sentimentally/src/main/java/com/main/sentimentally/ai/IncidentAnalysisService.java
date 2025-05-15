package com.main.sentimentally.ai;

import com.main.sentimentally.dto.IncidentAIResponse;
import com.main.sentimentally.entity.Category;
import com.main.sentimentally.entity.Severity;
import com.main.sentimentally.record.IncidentAIRecord;
import com.main.sentimentally.service.CategoryService;
import com.main.sentimentally.service.SeverityService;
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
public class IncidentAnalysisService {

    private final OpenAiChatModel chatModel;

    private final CategoryService categoryService;

    private final SeverityService severityService;

    public IncidentAIResponse analyseData(String incidentText){
        String template =
                """
                    You are an intelligent incident classification engine. Given an incident report or description, analyze the content carefully and return a structured JSON response. Your task is to identify:
                    1. Category – Choose the most relevant category from the following list:{categories}
                    If none of these categories are suitable, generate a new relevant category as a string.
                    2. Severity – Assess the severity of the incident based on urgency, impact, and tone of the incident using these levels:{levels}.
                    3. Summary – Generate a concise and accurate summary of the incident in 1–2 sentences, capturing the key issue.
                    Input Format:
                    You will receive an unstructured text describing an incident.
                    Output Format:
                    Return the result in JSON format with the following structure:
                    {
                      "category": "<category-name>",
                      "severity": "<low|medium|high>",
                      "summary": "<brief summary of the incident>"
                    }
                    Example:
                    Incident Text: "The air conditioning in room 402 has been making loud noises all night, and the guest couldn't sleep."
                    Expected JSON Output:
                    {
                      "category": "Maintenance",
                      "severity": "medium",
                      "summary": "The air conditioning unit in room 402 is noisy, disturbing the guest's sleep."
                    }
                    Now, process the following incident: {incident}
                """;
        String categories =  StringUtils.joinWithComma(categoryService.getAllCategories().stream().map(Category::getName).toArray(String[]::new));
        String levels = StringUtils.joinWithComma(severityService.getAllSeverities().stream().map(Severity::getName).toArray(String[]::new));;
        IncidentAIResponse incidentAIResponse = new IncidentAIResponse();
        var outputParser = new BeanOutputConverter<>(IncidentAIRecord.class);
        String format = outputParser.getFormat();
        Message message = new SystemPromptTemplate(template).createMessage(
                Map.of("categories", categories, "levels", levels, "incident", incidentText, "format", format)
        );
        Prompt prompt = new Prompt(List.of(message));
        String generation = chatModel
                .call(prompt)
                .getResult()
                .getOutput().getText();
        assert generation != null;
        IncidentAIRecord incidentAIRecord = outputParser.convert(generation);
        assert incidentAIRecord != null;
        incidentAIResponse.setCategory(incidentAIRecord.category());
        incidentAIResponse.setSeverity(incidentAIRecord.severity());
        incidentAIResponse.setSummary(incidentAIRecord.summary());
        return incidentAIResponse;
    }
}
