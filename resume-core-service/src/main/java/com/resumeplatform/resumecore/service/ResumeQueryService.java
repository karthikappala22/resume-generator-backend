package com.resumeplatform.resumecore.service;

import com.resumeplatform.resumecore.dto.*;
import com.resumeplatform.resumecore.entity.Resume;
import com.resumeplatform.resumecore.entity.Role;
import com.resumeplatform.resumecore.entity.User;
import com.resumeplatform.resumecore.exception.AccessDeniedException;
import com.resumeplatform.resumecore.exception.ResourceNotFoundException;
import com.resumeplatform.resumecore.repository.ResumeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ResumeQueryService {

	private final ResumeRepository resumeRepository;
	private final UserService userService;

	public ResumeQueryService(ResumeRepository resumeRepository, UserService userService) {
		this.resumeRepository = resumeRepository;
		this.userService = userService;
	}
	@Transactional
	public ResumeResponse getFullResume(Long resumeId) {

		// 1️⃣ Get logged-in user
		User loggedInUser = userService.getLoggedInUser();

		// 2️⃣ Fetch resume
		Resume resume = resumeRepository.findFullResume(resumeId)
				.orElseThrow(() -> new ResourceNotFoundException("Resume not found"));

		// 3️⃣ OWNERSHIP CHECK (CRITICAL)

		if (loggedInUser.getRole() != Role.ADMIN && !resume.getUser().getId().equals(loggedInUser.getId())) {
			throw new AccessDeniedException("You are not allowed to access this resume");
		}

		// 4️⃣ Map to response
		ResumeResponse response = new ResumeResponse();
		response.setId(resume.getId());
		response.setTitle(resume.getTitle());
		response.setSkills(resume.getSkills());
		response.setUserId(resume.getUser().getId());
		response.setFullName(resume.getUser().getFullName());
		response.setEmail(resume.getUser().getEmail());

		response.setEducations(resume.getEducations().stream().map(e -> {
			EducationResponse er = new EducationResponse();
			er.setDegree(e.getDegree());
			er.setInstitution(e.getInstitution());
			er.setStartYear(e.getStartYear());
			er.setEndYear(e.getEndYear());
			return er;
		}).toList());

		response.setExperiences(resume.getExperiences().stream().map(exp -> {
			ExperienceResponse er = new ExperienceResponse();
			er.setCompanyName(exp.getCompanyName());
			er.setRole(exp.getRole());
			er.setStartDate(exp.getStartDate());
			er.setEndDate(exp.getEndDate());
			er.setBullets(exp.getBullets().stream().map(b -> b.getBulletText()).toList());
			return er;
		}).toList());

		response.setCertifications(resume.getCertifications().stream().map(c -> {
			CertificationResponse cr = new CertificationResponse();
			cr.setName(c.getName());
			cr.setIssuer(c.getIssuer());
			cr.setIssueDate(c.getIssueDate());
			cr.setExpiryDate(c.getExpiryDate());
			return cr;
		}).toList());

		return response;
	}
}
