package com.resume2role.service;

import com.resume2role.model.SkillSet;
import org.springframework.stereotype.Service;

@Service
public class ProfileScorer {

    public int calculateScore(SkillSet skills) {

        int score = 0;

        score += skills.getLanguages().size() * 10;
        score += skills.getFrameworks().size() * 12;
        score += skills.getDatabases().size() * 8;
        score += skills.getTools().size() * 6;
        score += skills.getConcepts().size() * 5;

        // Cap score at 100
        return Math.min(score, 100);
    }

    public String getExperienceLevel(int score) {

        if (score < 30) return "Beginner";
        else if (score < 70) return "Intermediate";
        else return "Advanced";
    }
}