package com.resume2role.service;

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

    public String analyzeResume(String resumeText) {

        try {
            String prompt = "You are a technical interviewer.\n" +
                    "Generate exactly 2 interview questions.\n" +
                    "Return ONLY questions, no explanations.\n" +
                    "Each question on a new line.\n\n" +
                    "Resume:\n" + resumeText;

            String safePrompt = prompt
                    .replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\n", "\\n");

            String requestBody = """
            {
              "contents": [{
                "parts":[{"text": "%s"}]
              }]
            }
            """.formatted(safePrompt);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(
                            "https://generativelanguage.googleapis.com/v1beta/models/gemini-flash-latest:generateContent?key=" + apiKey
                    ))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            return extractText(response.body());

        } catch (Exception e) {
            return "Gemini Error: " + e.getMessage();
        }
    }

    private String extractText(String json) {
        try {
            String marker = "\"text\": \"";
            int start = json.indexOf(marker);

            if (start == -1) return json;

            start += marker.length();

            StringBuilder result = new StringBuilder();
            boolean escape = false;

            for (int i = start; i < json.length(); i++) {
                char c = json.charAt(i);

                if (escape) {
                    result.append(c);
                    escape = false;
                } else if (c == '\\') {
                    escape = true;
                } else if (c == '"') {
                    break;
                } else {
                    result.append(c);
                }
            }

            return result.toString();

        } catch (Exception e) {
            return json;
        }
    }
    public String evaluateAnswer(String question, String answer) {

        try {
            String prompt = "Evaluate the following answer.\n\n" +
                    "Question: " + question + "\n" +
                    "Answer: " + answer + "\n\n" +
                    "Return STRICT JSON:\n" +
                    "{\n" +
                    "  \"score\": number (0-10),\n" +
                    "  \"feedback\": \"...\",\n" +
                    "  \"improvement\": \"...\"\n" +
                    "}";

            String requestBody = """
        {
          "contents": [{
            "parts":[{"text": "%s"}]
          }]
        }
        """.formatted(prompt.replace("\"", "\\\""));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(
                            "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + apiKey
                    ))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}