package com.resumeplatform.resumecore.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "resumes")
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    // ---------------- USER ----------------
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    // ---------------- SKILLS ----------------
    @ElementCollection
    @CollectionTable(
            name = "resume_skills",
            joinColumns = @JoinColumn(name = "resume_id")
    )
    @Column(name = "skill")
    private List<String> skills= new ArrayList<>();

    // ---------------- EDUCATION ----------------
    @OneToMany(
            mappedBy = "resume",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @OrderBy("startYear DESC")
    private Set<Education> educations = new LinkedHashSet<>();

    // ---------------- EXPERIENCE ----------------
    @OneToMany(
            mappedBy = "resume",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @OrderBy("startDate DESC")
    private Set<Experience> experiences = new LinkedHashSet<>();

    // ---------------- CERTIFICATIONS ----------------
    @OneToMany(
            mappedBy = "resume",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @OrderBy("issueDate DESC")
    private Set<Certification> certifications = new LinkedHashSet<>();

    // ---------------- CONSTRUCTORS ----------------
    public Resume() {}

    public Resume(String title, User user) {
        this.title = title;
        this.user = user;
    }

    // ---------------- HELPER METHODS ----------------
    public void addEducation(Education education) {
        educations.add(education);
        education.setResume(this);
    }

    public void addExperience(Experience experience) {
        experiences.add(experience);
        experience.setResume(this);
    }

    public void addCertification(Certification certification) {
        certifications.add(certification);
        certification.setResume(this);
    }

    public void addSkill(String skill) {
        skills.add(skill);
    }

    // ---------------- GETTERS & SETTERS ----------------
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public Set<Education> getEducations() {
        return educations;
    }

    public Set<Experience> getExperiences() {
        return experiences;
    }

    public Set<Certification> getCertifications() {
        return certifications;
    }
}
