package com.resume2role.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "interviews")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Interview {

    @Id
    private String id;

    private String userId;
    private String resumeId;

    private List<String> questions;
    private List<Answer> answers;

    private int currentQuestionIndex;

    private String status; // STARTED, COMPLETED

    private LocalDateTime startedAt;
}