package com.resume2role.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private final ObjectMapper objectMapper =
            new ObjectMapper();

    public String analyzeResume(String resumeText) {

        try {

            String prompt =
                    """
                    You are a technical interviewer.

                    Generate easy and beginner friendly straight-forward technical interview questions based on the candidate's resume.

                    Requirements:
                    - Return only interview questions
                    - Put each question on a separate line
                    - Do not include explanations
                    - Do not combine questions into paragraphs

                    Resume:
                    """ + resumeText;

            String requestBody =
                    """
                    {
                      "contents": [{
                        "parts":[{"text": "%s"}]
                      }]
                    }
                    """
                            .formatted(
                                    prompt.replace("\"", "\\\"")
                            );

            HttpRequest request =
                    HttpRequest.newBuilder()
                            .uri(
                                    URI.create(
                                            "https://generativelanguage.googleapis.com/v1beta/models/gemini-flash-latest:generateContent?key="
                                                    + apiKey
                                    )
                            )
                            .header(
                                    "Content-Type",
                                    "application/json"
                            )
                            .POST(
                                    HttpRequest.BodyPublishers.ofString(
                                            requestBody
                                    )
                            )
                            .build();

            HttpResponse<String> response =
                    HttpClient.newHttpClient()
                            .send(
                                    request,
                                    HttpResponse.BodyHandlers.ofString()
                            );

            return extractText(
                    response.body()
            );

        } catch (Exception e) {

            return "Gemini Error: "
                    + e.getMessage();
        }
    }

    private String extractText(String json) {

        try {

            JsonNode root =
                    objectMapper.readTree(json);

            return root
                    .get("candidates")
                    .get(0)
                    .get("content")
                    .get("parts")
                    .get(0)
                    .get("text")
                    .asText();

        } catch (Exception e) {

            return json;
        }
    }

    public String evaluateAnswer(
            String question,
            String answer
    ) {

        try {

            String prompt =
                    """
                    Evaluate the following answer.

                    Question:
                    """ + question +

                            """
        
                            Answer:
                            """ + answer +

                            """
        
                            Return STRICT JSON:
                            {
                              "score": number (0-10),
                              "feedback": "...",
                              "improvement": "..."
                            }
                            """;

            String requestBody =
                    """
                    {
                      "contents": [{
                        "parts":[{"text": "%s"}]
                      }]
                    }
                    """
                            .formatted(
                                    prompt.replace("\"", "\\\"")
                            );

            HttpRequest request =
                    HttpRequest.newBuilder()
                            .uri(
                                    URI.create(
                                            "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key="
                                                    + apiKey
                                    )
                            )
                            .header(
                                    "Content-Type",
                                    "application/json"
                            )
                            .POST(
                                    HttpRequest.BodyPublishers.ofString(
                                            requestBody
                                    )
                            )
                            .build();

            HttpResponse<String> response =
                    HttpClient.newHttpClient()
                            .send(
                                    request,
                                    HttpResponse.BodyHandlers.ofString()
                            );

            return response.body();

        } catch (Exception e) {

            return "Error: "
                    + e.getMessage();
        }
    }
}