package com.resumeplatform.resumecore.controller;

import com.resumeplatform.resumecore.entity.Education;
import com.resumeplatform.resumecore.service.EducationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/resumes/{resumeId}/educations")
public class EducationController {

    private final EducationService educationService;

    public EducationController(EducationService educationService) {
        this.educationService = educationService;
    }

    @PostMapping
    public ResponseEntity<String> addEducation(
    		@PathVariable("resumeId") Long resumeId,
            @RequestBody Education education) {
    	
    	System.out.println("📥 Controller received request for resumeId = " + resumeId);

        educationService.addEducation(resumeId, education);
        return ResponseEntity.ok("Education added successfully");
    }
    
    @PutMapping("/{educationId}")
    public ResponseEntity<Void> updateEducation(
            @PathVariable Long educationId,
            @RequestBody Education request) {

        educationService.updateEducation(educationId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{educationId}")
    public ResponseEntity<Void> deleteEducation(@PathVariable Long educationId) {

        educationService.deleteEducation(educationId);
        return ResponseEntity.noContent().build();
    }

}
