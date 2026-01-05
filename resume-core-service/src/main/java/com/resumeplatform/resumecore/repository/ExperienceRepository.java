package com.resumeplatform.resumecore.repository;

import com.resumeplatform.resumecore.entity.Experience;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {
	
	Optional<Experience> findById(Long experienceId);

}
