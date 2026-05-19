package com.resume2role.service;

import com.resume2role.model.SkillSet;
import org.springframework.stereotype.Service;

@Service
public class RuleBasedRolePredictor {

    public String predictRole(SkillSet skills) {

        int backendScore = 0;
        int frontendScore = 0;
        int dataScore = 0;

        // Languages
        if (skills.getLanguages().contains("java")) backendScore += 3;
        if (skills.getLanguages().contains("python")) dataScore += 2;
        if (skills.getLanguages().contains("javascript")) frontendScore += 3;

        // Frameworks
        if (skills.getFrameworks().contains("spring")) backendScore += 3;
        if (skills.getFrameworks().contains("react")) frontendScore += 3;
        if (skills.getFrameworks().contains("django")) backendScore += 2;

        // Databases
        if (skills.getDatabases().contains("mysql")) backendScore += 2;
        if (skills.getDatabases().contains("mongodb")) backendScore += 2;

        // Decide role
        if (backendScore >= frontendScore && backendScore >= dataScore)
            return "Backend Developer";

        if (frontendScore >= backendScore && frontendScore >= dataScore)
            return "Frontend Developer";

        if (dataScore >= backendScore && dataScore >= frontendScore)
            return "Data Engineer";

        return "Software Developer";
    }
}