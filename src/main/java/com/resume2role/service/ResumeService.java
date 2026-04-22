package com.resume2role.service;

import com.resume2role.model.TechnicalProfile;
import com.resume2role.model.SkillSet;
import com.resume2role.model.Resume;
import com.resume2role.repository.ResumeRepository;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class ResumeService {

    private final RuleBasedRolePredictor rulePredictor;
    private final LLMRolePredictor llmPredictor;
    private final ResumeRepository resumeRepository;
    private final SkillExtractor skillExtractor;
    private final ProfileScorer profileScorer;
    private final GeminiService geminiService;
    private final FirebaseStorageService firebaseStorageService;

    public ResumeService(ResumeRepository resumeRepository,
                         SkillExtractor skillExtractor,
                         RuleBasedRolePredictor rulePredictor,
                         LLMRolePredictor llmPredictor,
                         ProfileScorer profileScorer,
                         GeminiService geminiService,
                         FirebaseStorageService firebaseStorageService) {

        this.resumeRepository = resumeRepository;
        this.skillExtractor = skillExtractor;
        this.rulePredictor = rulePredictor;
        this.llmPredictor = llmPredictor;
        this.profileScorer = profileScorer;
        this.geminiService = geminiService;
        this.firebaseStorageService = firebaseStorageService;
    }

    public Resume uploadResume(String userId, MultipartFile file) throws Exception {

        String fileUrl = firebaseStorageService.uploadFile(
                file.getBytes(),
                file.getOriginalFilename()
        );

        Tika tika = new Tika();
        String extractedText = tika.parseToString(file.getInputStream());

        String aiAnalysis = geminiService.analyzeResume(extractedText);

        Map<String, Object> parsedData = parseResume(extractedText);
        parsedData.put("aiAnalysis", aiAnalysis);

        SkillSet skills = skillExtractor.extractSkills(extractedText);

        String ruleRole = rulePredictor.predictRole(skills);
        String llmRole = llmPredictor.predictRole(extractedText);

        String finalRole = !llmRole.equalsIgnoreCase("Software Developer")
                ? llmRole
                : ruleRole;

        int score = profileScorer.calculateScore(skills);
        String experienceLevel = profileScorer.getExperienceLevel(score);

        TechnicalProfile technicalProfile = TechnicalProfile.builder()
                .skills(skills)
                .projects(new ArrayList<>())
                .predictedRole(finalRole)
                .experienceLevel(experienceLevel)
                .score(score)
                .build();

        Resume resume = Resume.builder()
                .userId(userId)
                .fileName(file.getOriginalFilename())
                .fileUrl(fileUrl) // ✅ NEW FIELD
                .extractedText(extractedText)
                .parsedData(parsedData)
                .uploadedAt(LocalDateTime.now())
                .technicalProfile(technicalProfile)
                .build();

        return resumeRepository.save(resume);
    }

    public void deleteResume(String resumeId) {

        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new RuntimeException("Resume not found"));

        if (resume.getFileUrl() != null) {
            firebaseStorageService.deleteFile(resume.getFileUrl());
        }

        resumeRepository.deleteById(resumeId);
    }

    private Map<String, Object> parseResume(String text) {
        Map<String, Object> data = new HashMap<>();

        String email = text.replaceAll("(?s).*?(\\b[\\w.-]+@[\\w.-]+\\.\\w+\\b).*", "$1");
        data.put("email", email);

        return data;
    }
}