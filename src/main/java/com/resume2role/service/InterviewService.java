package com.resume2role.service;

import com.resume2role.model.Answer;
import com.resume2role.model.Evaluation;
import com.resume2role.model.Interview;
import com.resume2role.model.Resume;
import com.resume2role.repository.InterviewRepository;
import com.resume2role.repository.ResumeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class InterviewService {

    private final InterviewRepository interviewRepository;
    private final ResumeRepository resumeRepository;
    private final InterviewServiceHelper helper;
    private final EvaluationService evaluationService;
    private final ResultService resultService;

    public InterviewService(InterviewRepository interviewRepository,
                            ResumeRepository resumeRepository,
                            InterviewServiceHelper helper,
                            EvaluationService evaluationService,
                            ResultService resultService) {
        this.interviewRepository = interviewRepository;
        this.resumeRepository = resumeRepository;
        this.helper = helper;
        this.evaluationService = evaluationService;
        this.resultService = resultService;
    }

    public Interview startInterview(String resumeId) {

        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new RuntimeException("Resume not found"));

        String questionsText = helper.generateQuestions(resume.getExtractedText());

        List<String> questions = Arrays.asList(questionsText.split("\\n"));

        Interview interview = Interview.builder()
                .userId(resume.getUserId())
                .resumeId(resumeId)
                .questions(questions)
                .answers(new ArrayList<>())
                .currentQuestionIndex(0)
                .status("STARTED")
                .startedAt(LocalDateTime.now())
                .build();

        return interviewRepository.save(interview);
    }

    public String getNextQuestion(String interviewId) {

        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new RuntimeException("Interview not found"));

        if (interview.getCurrentQuestionIndex() >= interview.getQuestions().size()) {
            interview.setStatus("COMPLETED");
            interviewRepository.save(interview);
            return "Interview completed";
        }

        return interview.getQuestions().get(interview.getCurrentQuestionIndex());
    }

    public Interview submitAnswer(String interviewId, String answerText, String transcript, Long duration) {

        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new RuntimeException("Interview not found"));

        String currentQuestion = interview.getQuestions()
                .get(interview.getCurrentQuestionIndex());

        Evaluation eval = evaluationService.evaluate(currentQuestion, answerText);

        Answer answer = Answer.builder()
                .question(currentQuestion)
                .userAnswer(answerText)
                .transcript(transcript)
                .duration(duration)
                .evaluation(eval)
                .build();

        interview.getAnswers().add(answer);
        interview.setCurrentQuestionIndex(interview.getCurrentQuestionIndex() + 1);

        return interviewRepository.save(interview);
    }
    public Interview getInterviewById(String id) {
        return interviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Interview not found"));
    }
}