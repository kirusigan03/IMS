package com.smartjob.resume_service.service;

import com.smartjob.resume_service.dto.CreateResumeRequest;
import com.smartjob.resume_service.entity.Resume;
import com.smartjob.resume_service.repository.ResumeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResumeService {

    private final ResumeRepository resumeRepository;

    public ResumeService(
            ResumeRepository resumeRepository
    ) {
        this.resumeRepository = resumeRepository;
    }

    public Resume createResume(
            Long candidateId,
            CreateResumeRequest request
    ) {

        if (request.isPrimaryResume()) {

            List<Resume> existingResumes =
                    resumeRepository
                            .findByCandidateId(
                                    candidateId
                            );

            existingResumes.forEach(
                    resume ->
                            resume.setPrimaryResume(false)
            );

            resumeRepository.saveAll(
                    existingResumes
            );
        }

        Resume resume = new Resume();

        resume.setCandidateId(
                candidateId
        );

        resume.setFileName(
                request.getFileName()
        );

        resume.setFileUrl(
                request.getFileUrl()
        );

        resume.setFileType(
                request.getFileType()
        );

        resume.setPrimaryResume(
                request.isPrimaryResume()
        );

        resume.setExtractedText(
                request.getExtractedText()
        );

        return resumeRepository.save(resume);
    }

    public Resume getResume(Long id) {

        return resumeRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Resume not found"
                        )
                );
    }

    public List<Resume> getCandidateResumes(
            Long candidateId
    ) {

        return resumeRepository
                .findByCandidateId(candidateId);
    }

    public void deleteResume(Long id) {

        Resume resume = getResume(id);

        resumeRepository.delete(resume);
    }
}