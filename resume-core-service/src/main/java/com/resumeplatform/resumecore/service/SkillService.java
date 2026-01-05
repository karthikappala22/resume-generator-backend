package com.resumeplatform.resumecore.service;

import com.resumeplatform.resumecore.entity.Resume;
import com.resumeplatform.resumecore.entity.Role;
import com.resumeplatform.resumecore.entity.User;
import com.resumeplatform.resumecore.exception.AccessDeniedException;
import com.resumeplatform.resumecore.exception.ResourceNotFoundException;
import com.resumeplatform.resumecore.repository.ResumeRepository;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SkillService {

    private final ResumeRepository resumeRepository;
    private final  UserService userService;

    public SkillService(ResumeRepository resumeRepository, UserService userService) {
        this.resumeRepository = resumeRepository;
		this.userService = userService;
    }

    public void addSkills(Long resumeId, Iterable<String> skills) {

		User loggedInUser = userService.getLoggedInUser();

		Resume resume = resumeRepository.findById(resumeId)
				.orElseThrow(() -> new ResourceNotFoundException("Resume not found"));

		if (loggedInUser.getRole() != Role.ADMIN && !resume.getUser().getId().equals(loggedInUser.getId())) {
			throw new AccessDeniedException("You are not allowed to access this resume");
		}

        for (String skill : skills) {
            resume.addSkill(skill);
        }

        // Save aggregate root
        resumeRepository.save(resume);
    }
    
	@Transactional
	public void updateSkills(Long resumeId, Iterable<String> skills) {

		User loggedInUser = userService.getLoggedInUser();

		Resume resume = resumeRepository.findById(resumeId).orElseThrow(() -> new RuntimeException("Resume not found"));

		if (!resume.getUser().getId().equals(loggedInUser.getId())) {
			throw new RuntimeException("Access denied");
		}

		resume.getSkills().clear();
		for (String skill : skills) {
			resume.addSkill(skill);
		}
	}
    
    
}
