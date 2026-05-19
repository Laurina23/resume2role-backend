package com.resume2role.service;

import org.springframework.stereotype.Service;

@Service
public class LLMRolePredictor {

    public String predictRole(String resumeText) {

        if (resumeText.toLowerCase().contains("spring"))
            return "Backend Developer";

        if (resumeText.toLowerCase().contains("react"))
            return "Frontend Developer";

        return "Software Developer";
    }
}