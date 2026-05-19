package com.resume2role.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterviewCard {

    private String interviewId;
    private String resumeId;

    private double score;
    private String status;

    private LocalDateTime startedAt;
}