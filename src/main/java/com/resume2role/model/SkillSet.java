package com.resume2role.model;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SkillSet {

    private List<String> languages;
    private List<String> frameworks;
    private List<String> databases;
    private List<String> tools;
    private List<String> concepts;
}