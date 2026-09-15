package com.smartjob.ai_service.controller;

import com.smartjob.ai_service.entity.ResumeAnalysis;
import com.smartjob.ai_service.repository.ResumeAnalysisRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    private final ResumeAnalysisRepository
            analysisRepository;

    public AiController(
            ResumeAnalysisRepository analysisRepository
    ) {
        this.analysisRepository =
                analysisRepository;
    }

    @GetMapping("/resume/{resumeId}")
    public ResponseEntity<ResumeAnalysis>
    getResumeAnalysis(
            @PathVariable Long resumeId
    ) {

        ResumeAnalysis analysis =
                analysisRepository
                        .findByResumeId(resumeId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Analysis not found"
                                )
                        );

        return ResponseEntity.ok(analysis);
    }
}
