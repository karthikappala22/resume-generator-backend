package com.resumeplatform.resumecore.service;

import com.resumeplatform.resumecore.dto.ExperienceRequest;
import com.resumeplatform.resumecore.entity.Experience;
import com.resumeplatform.resumecore.entity.ExperienceBullet;
import com.resumeplatform.resumecore.entity.Resume;
import com.resumeplatform.resumecore.entity.Role;
import com.resumeplatform.resumecore.entity.User;
import com.resumeplatform.resumecore.exception.AccessDeniedException;
import com.resumeplatform.resumecore.exception.ResourceNotFoundException;
import com.resumeplatform.resumecore.repository.ExperienceRepository;
import com.resumeplatform.resumecore.repository.ResumeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ExperienceService {

	private final ResumeRepository resumeRepository;
	private final UserService userService;
	private final ExperienceRepository experienceRepository;

	public ExperienceService(ResumeRepository resumeRepository, UserService userService,
			ExperienceRepository experienceRepository) {
		this.resumeRepository = resumeRepository;
		this.userService = userService;
		this.experienceRepository = experienceRepository;
	}

	@Transactional
	public void addExperience(Long resumeId, ExperienceRequest request) {

		User loggedInUser = userService.getLoggedInUser();

		Resume resume = resumeRepository.findById(resumeId)
				.orElseThrow(() -> new ResourceNotFoundException("Resume not found"));

		if (loggedInUser.getRole() != Role.ADMIN && !resume.getUser().getId().equals(loggedInUser.getId())) {
			throw new AccessDeniedException("You are not allowed to access this resume");
		}

		Experience experience = request.toExperience();

		// Attach experience to resume
		resume.addExperience(experience);

		// Attach bullets
		if (request.getBullets() != null) {
			for (String text : request.getBullets()) {
				ExperienceBullet bullet = new ExperienceBullet();
				bullet.setBulletText(text);
				experience.addBullet(bullet);
			}
		}

		// Save aggregate root
		resumeRepository.save(resume);
	}

	@Transactional
	public void updateExperience(Long experienceId, ExperienceRequest request) {

		User loggedInUser = userService.getLoggedInUser();

		Experience experience = experienceRepository.findById(experienceId)
				.orElseThrow(() -> new RuntimeException("Experience not found"));

		if (!experience.getResume().getUser().getId().equals(loggedInUser.getId())) {
			throw new RuntimeException("Access denied");
		}

		experience.setCompanyName(request.getCompanyName());
		experience.setRole(request.getRole());
		experience.setStartDate(request.getStartDate());
		experience.setEndDate(request.getEndDate());

		// 🔁 Replace bullets
		experience.getBullets().clear();

		if (request.getBullets() != null) {
			for (String bulletText : request.getBullets()) {
				ExperienceBullet bullet = new ExperienceBullet();
				bullet.setBulletText(bulletText);
				experience.addBullet(bullet);
			}
		}
	}

	@Transactional
	public void deleteExperience(Long experienceId) {

		User loggedInUser = userService.getLoggedInUser();

		Experience experience = experienceRepository.findById(experienceId)
				.orElseThrow(() -> new RuntimeException("Experience not found"));

		// 🔐 Ownership check
		if (!experience.getResume().getUser().getId().equals(loggedInUser.getId())) {
			throw new RuntimeException("Access denied");
		}

		experienceRepository.delete(experience);
	}

}
