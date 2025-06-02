package com.institute.repository.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.institute.entity.payment.PaymentEntity;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

	@Query(value = "select * from payment where candidate_id  = :candidateId ", nativeQuery = true)
	PaymentEntity getPaymentByCandidateId(@Param("candidateId") Long candidateId);

}
