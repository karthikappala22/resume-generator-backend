package com.resumeplatform.resumecore.service;

import com.resumeplatform.resumecore.dto.CreateResumeRequest;
import com.resumeplatform.resumecore.dto.ResumeResponse;
import com.resumeplatform.resumecore.entity.Resume;
import com.resumeplatform.resumecore.entity.Role;
import com.resumeplatform.resumecore.entity.User;
import com.resumeplatform.resumecore.exception.AccessDeniedException;
import com.resumeplatform.resumecore.exception.ResourceNotFoundException;
import com.resumeplatform.resumecore.repository.ResumeRepository;
import com.resumeplatform.resumecore.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;
    private final  UserService userService;

    public ResumeService(ResumeRepository resumeRepository,
                         UserRepository userRepository,  UserService userService) {
        this.resumeRepository = resumeRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Transactional
    public ResumeResponse createResume(CreateResumeRequest request) {

    	
    	User loggedInUser = userService.getLoggedInUser();
    	
		/*
		 * if(!loggedInUser.getId().equals(userId)) { throw new
		 * RuntimeException("Access denied"); }
		 * 
		 * //if (!resume.getUser().getId().equals(loggedInUser.getId())) { // throw new
		 * RuntimeException("Access denied"); //}
		 * 
		 * User user = userRepository.findById(userId) .orElseThrow(() -> new
		 * RuntimeException("User not found"));
		 */

        Resume resume = new Resume();
        resume.setTitle(request.getTitle());

        // relationship management
        loggedInUser.addResume(resume);

        Resume savedResume = resumeRepository.save(resume);

        ResumeResponse response = new ResumeResponse();
        response.setId(savedResume.getId());
        response.setTitle(savedResume.getTitle());

        return response;
    }

    @Transactional(readOnly = true)
    public List<ResumeResponse> getResumesByUser() {
    	
    	User loggedInUser = userService.getLoggedInUser();

        return resumeRepository.findByUserId(loggedInUser.getId())
                .stream()
                .map(r -> {
                    ResumeResponse response = new ResumeResponse();
                    response.setId(r.getId());
                    response.setTitle(r.getTitle());
                    return response;
                })
                .toList();
    }
    
    @Transactional
    public void updateResume(Long resumeId, CreateResumeRequest request) {

     //   User loggedInUser = userService.getLoggedInUser();

        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new RuntimeException("Resume not found"));

      //  if (!resume.getUser().getId().equals(loggedInUser.getId())) {
       //     throw new RuntimeException("Access denied");
       // }

        resume.setTitle(request.getTitle());
    }
    
    @Transactional
	public void deleteResume(Long resumeId) {

		User loggedInUser = userService.getLoggedInUser();

		Resume resume = resumeRepository.findFullResume(resumeId)
		        .orElseThrow(() ->
		            new ResourceNotFoundException("Resume not found")
		        );

		if (loggedInUser.getRole() != Role.ADMIN &&
		    !resume.getUser().getId().equals(loggedInUser.getId())) {
		    throw new AccessDeniedException("You are not allowed to access this resume");
		}

		resumeRepository.delete(resume);
	}
}
