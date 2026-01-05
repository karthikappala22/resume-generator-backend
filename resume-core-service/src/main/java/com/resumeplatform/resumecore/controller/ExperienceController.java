package com.resumeplatform.resumecore.controller;

import com.resumeplatform.resumecore.dto.ExperienceRequest;
import com.resumeplatform.resumecore.service.ExperienceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/resumes/{resumeId}/experiences")
public class ExperienceController {

    private final ExperienceService experienceService;

    public ExperienceController(ExperienceService experienceService) {
        this.experienceService = experienceService;
    }

    @PostMapping
    public ResponseEntity<String> addExperience(
            @PathVariable Long resumeId,
            @RequestBody ExperienceRequest request) {

        experienceService.addExperience(resumeId, request);
        return ResponseEntity.ok("Experience added successfully");
    }
    
    @DeleteMapping("/{experienceId}")
    public ResponseEntity<Void> deleteExperience(
            @PathVariable Long experienceId) {

        experienceService.deleteExperience(experienceId);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{experienceId}")
    public ResponseEntity<Void> updateExperience(
            @PathVariable Long experienceId,
            @RequestBody ExperienceRequest request) {

        experienceService.updateExperience(experienceId, request);
        return ResponseEntity.ok().build();
    }
    
    

}
