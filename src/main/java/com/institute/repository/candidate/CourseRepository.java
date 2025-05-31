package com.institute.repository.candidate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.institute.entity.candidate.CourseEntity;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface CourseRepository extends JpaRepository<CourseEntity, Long> {

	@Query(value = "select * from course where candidate_id  = :candidateId ", nativeQuery = true)
	CourseEntity getCourseByCandidateId(@Param("candidateId") Long candidateId);

}
