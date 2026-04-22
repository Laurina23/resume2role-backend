package com.resume2role.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Evaluation {

    private String question;
    private String answer;

    private int score;           // 0–10
    private String feedback;     // strengths
    private String improvement;  // suggestions
}