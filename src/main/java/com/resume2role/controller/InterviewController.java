package com.resume2role.controller;

import com.resume2role.dto.InterviewCard;
import com.resume2role.dto.InterviewResult;
import com.resume2role.model.Interview;
import com.resume2role.service.DashboardService;
import com.resume2role.service.InterviewService;
import com.resume2role.service.ResultService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/interview")
public class InterviewController {

    private final InterviewService interviewService;
    private final ResultService resultService;
    private final DashboardService dashboardService;

    public InterviewController(InterviewService interviewService,
                               ResultService resultService,
                               DashboardService dashboardService) {
        this.interviewService = interviewService;
        this.resultService = resultService;
        this.dashboardService = dashboardService;
    }
    @GetMapping("/user/{userId}")
    public List<InterviewCard> getUserInterviews(@PathVariable String userId) {
        return dashboardService.getUserInterviews(userId);
    }

    @GetMapping("/{id}/result")
    public InterviewResult getResult(@PathVariable String id) {
        return resultService.generateResult(id);
    }

    @PostMapping("/start/{resumeId}")
    public Interview startInterview(@PathVariable String resumeId) {
        return interviewService.startInterview(resumeId);
    }

    @GetMapping("/{id}/next")
    public String getNextQuestion(@PathVariable String id) {
        return interviewService.getNextQuestion(id);
    }

    @PostMapping("/{id}/answer")
    public Interview submitAnswer(@PathVariable String id,
                                  @RequestBody Map<String, Object> body) {

        String answer = (String) body.get("answer");
        String transcript = (String) body.get("transcript");
        Long duration = body.get("duration") != null
                ? Long.valueOf(body.get("duration").toString())
                : null;

        return interviewService.submitAnswer(id, answer, transcript, duration);
    }
    @GetMapping("/{id}")
    public Interview getInterview(@PathVariable String id) {
        return interviewService.getInterviewById(id);
    }
}