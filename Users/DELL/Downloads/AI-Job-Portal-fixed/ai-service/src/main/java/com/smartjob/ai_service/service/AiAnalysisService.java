package com.smartjob.ai_service.service;

import com.smartjob.ai_service.entity.ResumeAnalysis;
import com.smartjob.ai_service.event.ApplicationSubmittedEvent;
import com.smartjob.ai_service.repository.ResumeAnalysisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class AiAnalysisService {

    private static final Logger log =
            LoggerFactory.getLogger(AiAnalysisService.class);

    private final ResumeAnalysisRepository
            analysisRepository;

    public AiAnalysisService(
            ResumeAnalysisRepository analysisRepository
    ) {
        this.analysisRepository =
                analysisRepository;
    }

    @KafkaListener(
            topics = "application-submitted",
            groupId = "ai-service"
    )
    public void handleApplicationSubmitted(
            ApplicationSubmittedEvent event
    ) {

        log.info(
                "AI Service received application: {}",
                event.getApplicationId()
        );

        if (event.getResumeId() == null) {

            log.info(
                    "No resume attached to application {}. Skipping AI analysis.",
                    event.getApplicationId()
            );

            return;
        }

        ResumeAnalysis analysis =
                analyzeResume(
                        event.getResumeId()
                );

        analysisRepository.save(analysis);
    }

    public ResumeAnalysis analyzeResume(
            Long resumeId
    ) {

        /*
         * Temporary analysis.
         *
         * Later this method will call
         * an actual AI model/API.
         */

        ResumeAnalysis analysis =
                new ResumeAnalysis();

        analysis.setResumeId(resumeId);

        analysis.setOverallScore(75);

        analysis.setSkillsScore(80);

        analysis.setExperienceScore(70);

        analysis.setEducationScore(75);

        analysis.setExtractedSkills(
                "Java, Spring Boot, React, PostgreSQL"
        );

        analysis.setMissingSkills(
                "Docker, Kubernetes, Kafka"
        );

        analysis.setFeedback(
                "Good technical foundation. " +
                "Consider improving cloud, " +
                "containerization and event-driven " +
                "architecture skills."
        );

        return analysis;
    }
}
