package com.resume2role.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.resume2role.model.Evaluation;
import org.springframework.stereotype.Service;

@Service
public class EvaluationService {

    private final GeminiService geminiService;

    public EvaluationService(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    public Evaluation evaluate(String question, String answer) {

        try {
            String raw = geminiService.evaluateAnswer(question, answer);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(raw);

            String text = root
                    .path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text")
                    .asText();

            JsonNode parsed = mapper.readTree(text);

            return Evaluation.builder()
                    .question(question)
                    .answer(answer)
                    .score(parsed.path("score").asInt())
                    .feedback(parsed.path("feedback").asText())
                    .improvement(parsed.path("improvement").asText())
                    .build();

        } catch (Exception e) {
            return Evaluation.builder()
                    .question(question)
                    .answer(answer)
                    .score(0)
                    .feedback("Evaluation failed")
                    .improvement(e.getMessage())
                    .build();
        }
    }
}