package com.smartjob.ai_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "resume_analysis")
public class ResumeAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long resumeId;

    private Integer overallScore;

    private Integer skillsScore;

    private Integer experienceScore;

    private Integer educationScore;

    @Column(length = 5000)
    private String extractedSkills;

    @Column(length = 5000)
    private String missingSkills;

    @Column(length = 10000)
    private String feedback;

    private LocalDateTime createdAt;

    public ResumeAnalysis() {
    }

    @PrePersist
    public void onCreate() {

        createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Long getResumeId() {
        return resumeId;
    }

    public void setResumeId(Long resumeId) {
        this.resumeId = resumeId;
    }

    public Integer getOverallScore() {
        return overallScore;
    }

    public void setOverallScore(Integer overallScore) {
        this.overallScore = overallScore;
    }

    public Integer getSkillsScore() {
        return skillsScore;
    }

    public void setSkillsScore(Integer skillsScore) {
        this.skillsScore = skillsScore;
    }

    public Integer getExperienceScore() {
        return experienceScore;
    }

    public void setExperienceScore(
            Integer experienceScore
    ) {
        this.experienceScore = experienceScore;
    }

    public Integer getEducationScore() {
        return educationScore;
    }

    public void setEducationScore(
            Integer educationScore
    ) {
        this.educationScore = educationScore;
    }

    public String getExtractedSkills() {
        return extractedSkills;
    }

    public void setExtractedSkills(
            String extractedSkills
    ) {
        this.extractedSkills = extractedSkills;
    }

    public String getMissingSkills() {
        return missingSkills;
    }

    public void setMissingSkills(
            String missingSkills
    ) {
        this.missingSkills = missingSkills;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
