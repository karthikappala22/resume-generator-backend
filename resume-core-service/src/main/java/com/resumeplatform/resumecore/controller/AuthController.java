package com.resumeplatform.resumecore.controller;

import com.resumeplatform.resumecore.security.JwtUtil;
import com.resumeplatform.resumecore.dto.AuthRequest;
import com.resumeplatform.resumecore.dto.AuthResponse;
import com.resumeplatform.resumecore.security.CustomUserDetailsService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

	/*
	 * @PostMapping("/login") public String login(@RequestParam String username,
	 * 
	 * @RequestParam String password) {
	 * 
	 * Authentication authentication = authenticationManager.authenticate( new
	 * UsernamePasswordAuthenticationToken(username, password) );
	 * 
	 * return jwtUtil.generateToken(username); }
	 */
    
    
    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()
                        )
                );

        String token = jwtUtil.generateToken(authentication.getName());

        return new AuthResponse(token);
    }
}
