package com.institute.repository.candidate;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.institute.entity.candidate.CommentEntity;

 
@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

	@Query(value = "select * from comments where candidate_id = :candidateId order by created_date desc", nativeQuery = true)
	List<CommentEntity> getComments(@Param("candidateId") Long candidateId);

	@Query(value = "select count(*) from comments where candidate_id = :candidateId", nativeQuery = true)
	Long getCommentsCount(@Param("candidateId") Long candidateId);
}
