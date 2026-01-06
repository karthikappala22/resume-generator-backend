package com.resumeplatform.resumecore.controller;

import com.resumeplatform.resumecore.dto.ResumeResponse;
import com.resumeplatform.resumecore.service.ResumeQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/resumes")
public class ResumeQueryController {

    private final ResumeQueryService resumeQueryService;

    public ResumeQueryController(ResumeQueryService resumeQueryService) {
        this.resumeQueryService = resumeQueryService;
    }

    @GetMapping("/{resumeId}/full")
    public ResponseEntity<ResumeResponse> getFullResume(@PathVariable("resumeId") Long resumeId) {
        return ResponseEntity.ok(resumeQueryService.getFullResume(resumeId));
    }
}
