package com.resumeplatform.resumecore.repository;

import com.resumeplatform.resumecore.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ResumeRepository extends JpaRepository<Resume, Long> {

    List<Resume> findByUserId(Long userId);
    
    
    @Query("""
            select distinct r from Resume r
            left join fetch r.educations
            left join fetch r.experiences e
            left join fetch e.bullets
            left join fetch r.certifications
            where r.id = :resumeId
        """)
        Optional<Resume> findFullResume(@Param("resumeId") Long resumeId);
}
