package com.resume2role.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterviewResult {

    private String interviewId;

    private double averageScore;

    private int totalQuestions;

    private List<String> strengths;

    private List<String> weaknesses;

    private String overallFeedback;
}