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

            String raw =
                    geminiService.evaluateAnswer(
                            question,
                            answer
                    );

            ObjectMapper mapper =
                    new ObjectMapper();

            JsonNode root =
                    mapper.readTree(raw);

            JsonNode candidates =
                    root.path("candidates");

            if (!candidates.isArray()
                    || candidates.isEmpty()) {

                return fallbackEvaluation(
                        question,
                        answer,
                        "No evaluation generated."
                );
            }

            String text =
                    candidates
                            .get(0)
                            .path("content")
                            .path("parts")
                            .get(0)
                            .path("text")
                            .asText();

            if (text == null
                    || text.isBlank()) {

                return fallbackEvaluation(
                        question,
                        answer,
                        "Empty AI evaluation response."
                );
            }

            text = text
                    .replace("```json", "")
                    .replace("```", "")
                    .trim();

            JsonNode parsed =
                    mapper.readTree(text);

            int score =
                    parsed.path("score")
                            .asInt(0);

            String feedback =
                    parsed.path("feedback")
                            .asText(
                                    "No feedback provided."
                            );

            String improvement =
                    parsed.path("improvement")
                            .asText(
                                    "No improvement suggestions."
                            );

            return Evaluation.builder()
                    .question(question)
                    .answer(answer)
                    .score(score)
                    .feedback(feedback)
                    .improvement(improvement)
                    .build();

        } catch (Exception e) {

            return fallbackEvaluation(
                    question,
                    answer,
                    "Evaluation parsing failed."
            );
        }
    }

    private Evaluation fallbackEvaluation(
            String question,
            String answer,
            String message
    ) {

        return Evaluation.builder()
                .question(question)
                .answer(answer)
                .score(5)
                .feedback(
                        "Answer was received successfully."
                )
                .improvement(message)
                .build();
    }
}