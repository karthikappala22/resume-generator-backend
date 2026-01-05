package com.resumeplatform.resumecore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.resumeplatform.resumecore.entity.Education;

public interface EducationRepository extends JpaRepository<Education, Long> {
}

