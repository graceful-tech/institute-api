package com.institute.repository.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.institute.entity.common.MessageEntity;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface MessageRepository extends JpaRepository<MessageEntity, Long> {

	@Query(value = "select * from make_profile.message where code = :code", nativeQuery = true)
	MessageEntity getMessageByCode(@Param("code") String code);

}
