package com.resume2role.controller;

import com.resume2role.model.Resume;
import com.resume2role.repository.ResumeRepository;
import com.resume2role.service.InterviewServiceHelper;
import com.resume2role.service.ResumeService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/resumes")
public class ResumeController {

    private final ResumeService resumeService;
    private final ResumeRepository resumeRepository;
    private final InterviewServiceHelper helper;

    public ResumeController(ResumeService resumeService,
                            ResumeRepository resumeRepository,
                            InterviewServiceHelper helper) {

        this.resumeService = resumeService;
        this.resumeRepository = resumeRepository;
        this.helper = helper;
    }

    @GetMapping("/{id}/questions")
    public String generateQuestions(@PathVariable String id) {

        Resume resume = resumeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resume not found"));

        return helper.generateQuestions(resume.getExtractedText());
    }

    @PostMapping("/upload")
    public Resume uploadResume(
            @RequestParam String userId,
            @RequestParam MultipartFile file
    ) throws Exception {
        return resumeService.uploadResume(userId, file);
    }
    @DeleteMapping("/{id}")
    public String deleteResume(@PathVariable String id) {
        resumeService.deleteResume(id);
        return "Resume deleted successfully";
    }
}