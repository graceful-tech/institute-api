package com.institute.repository.candidate;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.institute.entity.common.StatusEntity;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface StatusRepository extends JpaRepository<StatusEntity, Long> {

	@Query(value = "select * from status ", nativeQuery = true)
	List<StatusEntity> getAllStatus();

}
