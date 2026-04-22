package com.resume2role.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Answer {

    private String question;

    private String userAnswer;

    private String transcript;

    private Long duration;

    private Evaluation evaluation;
}