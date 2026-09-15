package com.smartjob.resume_service.controller;

import com.smartjob.resume_service.dto.CreateResumeRequest;
import com.smartjob.resume_service.entity.Resume;
import com.smartjob.resume_service.security.UserPrincipal;
import com.smartjob.resume_service.service.ResumeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resumes")
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(
            ResumeService resumeService
    ) {
        this.resumeService = resumeService;
    }

    @PostMapping
    public ResponseEntity<Resume> createResume(
            @Valid @RequestBody
            CreateResumeRequest request,
            Authentication authentication
    ) {

        UserPrincipal principal =
                (UserPrincipal) authentication.getPrincipal();

        if (!"JOB_SEEKER".equals(principal.getRole())) {

            throw new RuntimeException(
                    "Only job seekers can upload resumes"
            );
        }

        return ResponseEntity.ok(
                resumeService.createResume(
                        principal.getUserId(),
                        request
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resume> getResume(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                resumeService.getResume(id)
        );
    }

    @GetMapping("/my")
    public ResponseEntity<List<Resume>>
    getMyResumes(
            Authentication authentication
    ) {

        UserPrincipal principal =
                (UserPrincipal) authentication.getPrincipal();

        return ResponseEntity.ok(
                resumeService
                        .getCandidateResumes(
                                principal.getUserId()
                        )
        );
    }

    @GetMapping("/candidate/{candidateId}")
    public ResponseEntity<List<Resume>>
    getCandidateResumes(
            @PathVariable Long candidateId
    ) {

        return ResponseEntity.ok(
                resumeService
                        .getCandidateResumes(
                                candidateId
                        )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteResume(
            @PathVariable Long id
    ) {

        resumeService.deleteResume(id);

        return ResponseEntity.ok(
                "Resume deleted successfully"
        );
    }
}