package com.institute.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.institute.dto.user.UserDto;
import com.institute.entity.user.UserEntity;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<UserEntity, Long> {

	@Query(value = "select count(id) from user where email = :email or mobile_number = :mobileNumber ", nativeQuery = true)
	int findByMobileNumberAndEmail(@Param("mobileNumber") String mobileNumber, @Param("email") String email);

	@Query(value = "select id from users where user_name = :userName", nativeQuery = true)
	Long getUserId(@Param("userName") String userName);

	@Query(value = "select * from user where mobile_number = :mobileNumber ", nativeQuery = true)
	UserEntity findByMobileNumber(@Param("mobileNumber") String mobileNumber);

	@Query(value = "select * from user where user_name = :userName ", nativeQuery = true)
	UserEntity getUserByUserName(@Param("userName") String userName);

	@Query(value = "select count(*) from user where mobile_number = :mobileNumber or email = :email", nativeQuery = true)
	int getDuplicateUserCount(@Param("mobileNumber") String mobileNumber, @Param("email") String email);

	@Query(value = "select count(*) from user where id != :userId and (mobile_number = :mobileNumber or email = :email)", nativeQuery = true)
	int getDuplicateUserCount(@Param("mobileNumber") String mobileNumber, @Param("email") String email,
			@Param("userId") Long userId);

	@Query(value = "select count(*) from user where user_name = :userName", nativeQuery = true)
	int getDuplicateUserNameCount(@Param("userName") String userName);

	@Query(value = "select count(*) from users where id != :userId and user_name = :userName", nativeQuery = true)
	int getDuplicateUserNameCount(@Param("userName") String userName, @Param("userId") Long userId);

}
