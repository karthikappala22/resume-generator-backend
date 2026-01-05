package com.resumeplatform.resumecore.service;

import com.resumeplatform.resumecore.dto.CreateUserRequest;
import com.resumeplatform.resumecore.dto.UserResponse;
import com.resumeplatform.resumecore.entity.Role;
import com.resumeplatform.resumecore.entity.User;
import com.resumeplatform.resumecore.exception.ResourceNotFoundException;
import com.resumeplatform.resumecore.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public UserResponse createUser(CreateUserRequest request) {

		User user = new User();
		user.setEmail(request.getEmail());
		user.setFullName(request.getFullName());

		// ✅ PERMANENT SECURITY FIX
		user.setPassword(passwordEncoder.encode(request.getPassword()));

		// ✅ DEFAULT ROLE (SAFE)
		user.setRole(Role.USER);

		User savedUser = userRepository.save(user);

		return new UserResponse(savedUser.getId(), savedUser.getEmail(), savedUser.getFullName(),
				savedUser.getRole().name());
	}

	@Transactional(readOnly = true)
	public UserResponse getUser(Long id) {

		User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

		return new UserResponse(user.getId(), user.getEmail(), user.getFullName(), user.getRole().name());
	}

	@Transactional
	public UserResponse createAdmin(String email, String fullName, String rawPassword) {

		User admin = new User();
		admin.setEmail(email);
		admin.setFullName(fullName);
		admin.setPassword(passwordEncoder.encode(rawPassword));
		admin.setRole(Role.ADMIN);

		User saved = userRepository.save(admin);

		return new UserResponse(saved.getId(), saved.getEmail(), saved.getFullName(), saved.getRole().name());
	}

	public User getLoggedInUser() {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String email;

		if (principal instanceof UserDetails) {
			email = ((UserDetails) principal).getUsername();
		} else {
			email = principal.toString();
		}

		return userRepository.findByEmail(email)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException("Authenticated user not found"));
	}

}
