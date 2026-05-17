package com.resume2role.service;

import com.resume2role.dto.InterviewResult;
import com.resume2role.model.Answer;
import com.resume2role.model.Evaluation;
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
                .orElseThrow(() ->
                        new RuntimeException("Interview not found"));

        List<Answer> answers =
                interview.getAnswers();

        double totalScore = 0;

        int count = 0;

        List<String> strengths =
                new ArrayList<>();

        List<String> weaknesses =
                new ArrayList<>();

        for (Answer ans : answers) {

            Evaluation evaluation =
                    ans.getEvaluation();

            if (evaluation == null) {
                continue;
            }

            int score =
                    evaluation.getScore();

            totalScore += score;

            count++;

            String feedback =
                    evaluation.getFeedback();

            String improvement =
                    evaluation.getImprovement();

            if (score >= 7) {

                strengths.add(
                        feedback
                );

            } else {

                weaknesses.add(
                        improvement
                );
            }
        }

        double averageScore =
                count == 0
                        ? 0
                        : totalScore / count;

        String overallFeedback;

        if (averageScore >= 8) {

            overallFeedback =
                    "Excellent technical performance with strong communication and problem-solving skills.";

        } else if (averageScore >= 6) {

            overallFeedback =
                    "Good performance overall, but there are some areas that need improvement.";

        } else {

            overallFeedback =
                    "Needs significant improvement in technical depth, clarity, and confidence.";
        }

        return InterviewResult.builder()
                .interviewId(interviewId)
                .averageScore(averageScore)
                .totalQuestions(answers.size())
                .strengths(strengths)
                .weaknesses(weaknesses)
                .overallFeedback(overallFeedback)
                .build();
    }
}