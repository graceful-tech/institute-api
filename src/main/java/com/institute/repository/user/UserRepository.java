package com.institute.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.institute.entity.user.UserEntity;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<UserEntity, Long>{
	
	@Query(value = "select count(id) from user where email = :email or mobile_number = :mobileNumber ", nativeQuery = true)
	int findByMobileNumberAndEmail(@Param("mobileNumber") String mobileNumber, @Param("email") String email);

}
