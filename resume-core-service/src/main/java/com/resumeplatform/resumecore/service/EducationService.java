package com.resumeplatform.resumecore.service;

import com.resumeplatform.resumecore.entity.Education;
import com.resumeplatform.resumecore.entity.Resume;
import com.resumeplatform.resumecore.entity.Role;
import com.resumeplatform.resumecore.entity.User;
import com.resumeplatform.resumecore.exception.AccessDeniedException;
import com.resumeplatform.resumecore.exception.ResourceNotFoundException;
import com.resumeplatform.resumecore.repository.EducationRepository;
import com.resumeplatform.resumecore.repository.ResumeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EducationService {

	private final ResumeRepository resumeRepository;
	private final EducationRepository educationRepository;
	private final UserService userService;

	public EducationService(ResumeRepository resumeRepository, EducationRepository educationRepository,
			UserService userService) {
		this.resumeRepository = resumeRepository;
		this.educationRepository = educationRepository;
		this.userService = userService;
	}

	public void addEducation(Long resumeId, Education education) {

		User loggedInUser = userService.getLoggedInUser();

		Resume resume = resumeRepository.findById(resumeId)
				.orElseThrow(() -> new ResourceNotFoundException("Resume not found"));

		if (loggedInUser.getRole() != Role.ADMIN && !resume.getUser().getId().equals(loggedInUser.getId())) {
			throw new AccessDeniedException("You are not allowed to access this resume");
		}

		// Relationship management
		resume.addEducation(education);

		// Save parent → cascade saves child
		resumeRepository.save(resume);
	}

	@Transactional
	public void updateEducation(Long educationId, Education request) {

		User loggedInUser = userService.getLoggedInUser();

		Education education = educationRepository.findById(educationId)
				.orElseThrow(() -> new RuntimeException("Education not found"));

		if (!education.getResume().getUser().getId().equals(loggedInUser.getId())) {
			throw new RuntimeException("Access denied");
		}

		education.setDegree(request.getDegree());
		education.setInstitution(request.getInstitution());
		education.setStartYear(request.getStartYear());
		education.setEndYear(request.getEndYear());
	}

	@Transactional
	public void deleteEducation(Long educationId) {

		User loggedInUser = userService.getLoggedInUser();

		Education education = educationRepository.findById(educationId)
				.orElseThrow(() -> new RuntimeException("Education not found"));

		if (!education.getResume().getUser().getId().equals(loggedInUser.getId())) {
			throw new RuntimeException("Access denied");
		}

		educationRepository.delete(education);
	}
}
