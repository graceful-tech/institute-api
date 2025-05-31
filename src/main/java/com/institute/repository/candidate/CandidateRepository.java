package com.institute.repository.candidate;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.institute.entity.candidate.CandidateEntity;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface CandidateRepository extends JpaRepository<CandidateEntity, Long> {

	@Query(value = "select * from candidates where mobile_number  = :mobileNumber ", nativeQuery = true)
	CandidateEntity getCandidateDetailsByMobileNumber(@Param("mobileNumber") String mobileNumber);

	@Query(value = "select * from candidates where created_username = :createdUserName ", nativeQuery = true)
	CandidateEntity getCandidateByUserName(@Param("createdUserName") String createdUserName);

	@Query(value = "select * from candidates where id  = :id ", nativeQuery = true)
	CandidateEntity getCandidateById(@Param("id") Long id);

}
