package com.resume2role.service;

import org.springframework.stereotype.Service;

@Service
public class InterviewServiceHelper {

    private final GeminiService geminiService;

    public InterviewServiceHelper(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    public String generateQuestions(String resumeText) {

        String prompt = "Generate exactly 5 technical interview questions.\n" +
                "STRICT RULES:\n" +
                "- Each question must be on a NEW LINE\n" +
                "- Do NOT use numbering\n" +
                "- Do NOT combine questions\n" +
                "- Output ONLY questions\n\n" +
                "Resume:\n" + resumeText;

        return geminiService.analyzeResume(prompt);
    }
}