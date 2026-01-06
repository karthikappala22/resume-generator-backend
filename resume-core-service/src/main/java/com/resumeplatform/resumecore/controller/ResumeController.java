package com.resumeplatform.resumecore.controller;

import com.resumeplatform.resumecore.dto.CreateResumeRequest;
import com.resumeplatform.resumecore.dto.ResumeResponse;
import com.resumeplatform.resumecore.service.ResumeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/resumes")
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @PostMapping
    public ResponseEntity<ResumeResponse> createResume(
            
            @Valid @RequestBody CreateResumeRequest request) {

        return ResponseEntity.ok(
                resumeService.createResume(request)
        );
    }

    @GetMapping
    public ResponseEntity<List<ResumeResponse>> getResumes() {

        return ResponseEntity.ok(
                resumeService.getResumesByUser()
        );
    }
    
    @PutMapping("/{resumeId}")
    public ResponseEntity<Void> updateResume(
            @PathVariable Long resumeId,
            @RequestBody CreateResumeRequest request) {

        resumeService.updateResume(resumeId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{resumeId}")
    public ResponseEntity<Void> deleteResume(@PathVariable("resumeId") Long resumeId) {

        resumeService.deleteResume(resumeId);
        return ResponseEntity.noContent().build();
    }
    
    

}
