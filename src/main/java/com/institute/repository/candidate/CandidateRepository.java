package com.institute.repository.candidate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.institute.entity.candidate.CandidateEntity;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface CandidateRepository extends JpaRepository<CandidateEntity, Long> {

}
