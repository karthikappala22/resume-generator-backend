package com.resumeplatform.resumecore.controller;

import com.resumeplatform.resumecore.entity.Certification;
import com.resumeplatform.resumecore.service.CertificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/resumes/{resumeId}/certifications")
public class CertificationController {

    private final CertificationService certificationService;

    public CertificationController(CertificationService certificationService) {
        this.certificationService = certificationService;
    }

    @PostMapping
    public ResponseEntity<String> addCertification(
            @PathVariable Long resumeId,
            @RequestBody Certification certification) {

        certificationService.addCertification(resumeId, certification);
        return ResponseEntity.ok("Certification added successfully");
    }
    
    /**
     * UPDATE certification
     */
    @PutMapping("/{certificationId}")
    public ResponseEntity<Void> updateCertification(
            @PathVariable Long certificationId,
            @RequestBody Certification request) {

        certificationService.updateCertification(certificationId, request);
        return ResponseEntity.ok().build();
    }

    /**
     * DELETE certification
     */
    @DeleteMapping("/{certificationId}")
    public ResponseEntity<Void> deleteCertification(
            @PathVariable Long certificationId) {

        certificationService.deleteCertification(certificationId);
        return ResponseEntity.noContent().build();
    }
}
