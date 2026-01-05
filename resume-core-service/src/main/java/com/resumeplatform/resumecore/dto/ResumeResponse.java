package com.resumeplatform.resumecore.dto;

import java.util.List;

public class ResumeResponse {

    private Long id;
    private String title;
    
    private String fullName;
    private String email;
    
    public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	private List<EducationResponse> educations;
    private List<ExperienceResponse> experiences;
    private List<String> skills;
    private List<CertificationResponse> certifications;
    
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<EducationResponse> getEducations() {
        return educations;
    }

    public void setEducations(List<EducationResponse> educations) {
        this.educations = educations;
    }

    public List<ExperienceResponse> getExperiences() {
        return experiences;
    }

    public void setExperiences(List<ExperienceResponse> experiences) {
        this.experiences = experiences;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public List<CertificationResponse> getCertifications() {
        return certifications;
    }

    public void setCertifications(List<CertificationResponse> certifications) {
        this.certifications = certifications;
    }
}
