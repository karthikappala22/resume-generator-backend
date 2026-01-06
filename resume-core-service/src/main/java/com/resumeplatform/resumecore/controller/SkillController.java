package com.resumeplatform.resumecore.controller;

import com.resumeplatform.resumecore.dto.SkillsRequest;
import com.resumeplatform.resumecore.service.SkillService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("resumes/{resumeId}/skills")
public class SkillController {

    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @PostMapping
    public ResponseEntity<String> addSkills(
    		@PathVariable("resumeId") Long resumeId,
            @RequestBody SkillsRequest request) {

        skillService.addSkills(resumeId, request.getSkills());
        return ResponseEntity.ok("Skills added successfully");
    }
    
    @PutMapping
    public ResponseEntity<Void> updateSkills(
    		@PathVariable("resumeId") Long resumeId,
            @RequestBody SkillsRequest skills) {

        skillService.updateSkills(resumeId, skills.getSkills());
        return ResponseEntity.ok().build();
    }
}
