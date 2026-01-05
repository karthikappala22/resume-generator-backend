package com.resumeplatform.resumecore.dto;

import com.resumeplatform.resumecore.entity.Experience;

import java.time.LocalDate;
import java.util.List;

public class ExperienceRequest {

    private String companyName;
    private String role;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<String> bullets;

    public Experience toExperience() {
        Experience experience = new Experience();
        experience.setCompanyName(companyName);
        experience.setRole(role);
        experience.setStartDate(startDate);
        experience.setEndDate(endDate);
        return experience;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<String> getBullets() {
        return bullets;
    }

    public void setBullets(List<String> bullets) {
        this.bullets = bullets;
    }
}
