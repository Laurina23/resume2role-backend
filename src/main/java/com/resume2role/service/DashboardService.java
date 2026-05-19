package com.resume2role.service;

import com.resume2role.dto.InterviewCard;
import com.resume2role.model.Answer;
import com.resume2role.model.Interview;
import com.resume2role.repository.InterviewRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardService {

    private final InterviewRepository interviewRepository;

    public DashboardService(InterviewRepository interviewRepository) {
        this.interviewRepository = interviewRepository;
    }

    public List<InterviewCard> getUserInterviews(String userId) {

        List<Interview> interviews = interviewRepository.findByUserId(userId);
        List<InterviewCard> cards = new ArrayList<>();

        for (Interview interview : interviews) {

            double avgScore = calculateAverage(interview);

            cards.add(
                    InterviewCard.builder()
                            .interviewId(interview.getId())
                            .resumeId(interview.getResumeId())
                            .score(avgScore)
                            .status(interview.getStatus())
                            .startedAt(interview.getStartedAt())
                            .build()
            );
        }

        return cards;
    }

    private double calculateAverage(Interview interview) {

        double total = 0;
        int count = 0;

        for (Answer ans : interview.getAnswers()) {
            if (ans.getEvaluation() != null) {
                total += ans.getEvaluation().getScore();
                count++;
            }
        }

        return count == 0 ? 0 : total / count;
    }
}