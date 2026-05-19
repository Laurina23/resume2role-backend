package com.resume2role.model;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TechnicalProfile {

    private SkillSet skills;
    private List<String> projects;
    private String predictedRole;
    private String experienceLevel;
    private int score;
}