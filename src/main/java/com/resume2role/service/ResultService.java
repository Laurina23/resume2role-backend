package com.resume2role.service;

import com.resume2role.dto.InterviewResult;
import com.resume2role.model.Answer;
import com.resume2role.model.Interview;
import com.resume2role.repository.InterviewRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResultService {

    private final InterviewRepository interviewRepository;

    public ResultService(InterviewRepository interviewRepository) {
        this.interviewRepository = interviewRepository;
    }

    public InterviewResult generateResult(String interviewId) {

        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new RuntimeException("Interview not found"));

        List<Answer> answers = interview.getAnswers();

        double totalScore = 0;
        int count = 0;

        List<String> strengths = new ArrayList<>();
        List<String> weaknesses = new ArrayList<>();

        for (Answer ans : answers) {

            if (ans.getEvaluation() == null) continue;

            int score = ans.getEvaluation().getScore();
            totalScore += score;
            count++;

            if (score >= 7) {
                strengths.add(ans.getQuestion());
            } else {
                weaknesses.add(ans.getQuestion());
            }
        }

        double avg = count == 0 ? 0 : totalScore / count;

        String overallFeedback;

        if (avg >= 8) overallFeedback = "Excellent performance";
        else if (avg >= 6) overallFeedback = "Good, but needs improvement";
        else overallFeedback = "Needs significant improvement";

        return InterviewResult.builder()
                .interviewId(interviewId)
                .averageScore(avg)
                .totalQuestions(answers.size())
                .strengths(strengths)
                .weaknesses(weaknesses)
                .overallFeedback(overallFeedback)
                .build();
    }
}