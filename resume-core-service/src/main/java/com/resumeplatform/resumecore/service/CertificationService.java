package com.resumeplatform.resumecore.service;

import com.resumeplatform.resumecore.entity.Certification;
import com.resumeplatform.resumecore.entity.Resume;
import com.resumeplatform.resumecore.entity.Role;
import com.resumeplatform.resumecore.entity.User;
import com.resumeplatform.resumecore.exception.AccessDeniedException;
import com.resumeplatform.resumecore.exception.ResourceNotFoundException;
import com.resumeplatform.resumecore.repository.CertificationRepository;
import com.resumeplatform.resumecore.repository.ResumeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CertificationService {

	private final ResumeRepository resumeRepository;
	private final UserService userService;
	private final CertificationRepository certificationRepository;

	public CertificationService(ResumeRepository resumeRepository, UserService userService,
			CertificationRepository certificationRepository) {
		this.resumeRepository = resumeRepository;
		this.userService = userService;
		this.certificationRepository = certificationRepository;
	}
	@Transactional
	public void addCertification(Long resumeId, Certification certification) {

		User loggedInUser = userService.getLoggedInUser();

		Resume resume = resumeRepository.findById(resumeId)
				.orElseThrow(() -> new ResourceNotFoundException("Resume not found"));

		if (loggedInUser.getRole() != Role.ADMIN && !resume.getUser().getId().equals(loggedInUser.getId())) {
			throw new AccessDeniedException("You are not allowed to access this resume");
		}

		resume.addCertification(certification);

		resumeRepository.save(resume);
	}

	@Transactional
	public void deleteCertification(Long certificationId) {

		User loggedInUser = userService.getLoggedInUser();

		Certification cert = certificationRepository.findById(certificationId)
				.orElseThrow(() -> new RuntimeException("Certification not found"));

		if (!cert.getResume().getUser().getId().equals(loggedInUser.getId())) {
			throw new RuntimeException("Access denied");
		}

		certificationRepository.delete(cert);
	}
	
	@Transactional
	public void updateCertification(Long certificationId, Certification request) {

		Certification certification = certificationRepository.findById(certificationId)
				.orElseThrow(() -> new RuntimeException("Certification not found"));

		if (!certification.getResume().getUser().getId().equals(userService.getLoggedInUser().getId())) {
			throw new RuntimeException("Access denied");
		}

		certification.setName(request.getName());
		certification.setIssuer(request.getIssuer());
		certification.setIssueDate(request.getIssueDate());
		certification.setExpiryDate(request.getExpiryDate());
	}

}
