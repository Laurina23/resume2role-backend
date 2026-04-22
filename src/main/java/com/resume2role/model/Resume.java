package com.resume2role.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "resumes")
public class Resume {

    @Id
    private String id;

    private String userId;
    private String fileName;
    private String extractedText;
    private Map<String, Object> parsedData;
    private LocalDateTime uploadedAt;
    private TechnicalProfile technicalProfile;
    private String fileUrl;
}