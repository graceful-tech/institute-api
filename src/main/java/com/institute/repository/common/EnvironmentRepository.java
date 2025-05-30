package com.institute.repository.common;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.institute.entity.common.EnvironmentEntity;

 
@Repository
public interface EnvironmentRepository extends JpaRepository<EnvironmentEntity, Long> {

	@Query(value = "select environment_value from environment_config where environment_key = :environmentKey", nativeQuery = true)
	String getEnvironmentValueByKey(@Param("environmentKey") String environmentKey);

	@Query(value = "select * from environment_config where environment_key in ('RAZOR_PAY_KEY', 'RAZOR_PAY_SECRET')", nativeQuery = true)
	List<EnvironmentEntity> getRazorPayConfiguration();

}
