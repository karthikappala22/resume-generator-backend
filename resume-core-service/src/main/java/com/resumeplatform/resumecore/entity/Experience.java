package com.resumeplatform.resumecore.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "experiences")
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private LocalDate startDate;

    private LocalDate endDate;

    // Owning Resume
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "resume_id")
    private Resume resume;

    // Bullet points
    @OneToMany(
            mappedBy = "experience",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<ExperienceBullet> bullets = new LinkedHashSet<>();

    // ===== Helper methods =====
    public void addBullet(ExperienceBullet bullet) {
        bullets.add(bullet);
        bullet.setExperience(this);
    }

    public void removeBullet(ExperienceBullet bullet) {
        bullets.remove(bullet);
        bullet.setExperience(null);
    }

    // ===== Getters & Setters =====

    public Long getId() {
        return id;
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

    public Resume getResume() {
        return resume;
    }

    public void setResume(Resume resume) {
        this.resume = resume;
    }

    public Set<ExperienceBullet> getBullets() {
        return bullets;
    }
}
